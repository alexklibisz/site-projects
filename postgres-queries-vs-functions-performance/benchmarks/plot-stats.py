import pandas as pd
import sys
import matplotlib.pyplot as plt

def convert_to_mb(s):
    if 'kB' in s:
        return float(s.replace('kB', '')) * 0.001
    elif 'MB' in s:
        return float(s.replace('MB', ''))
    else:
        return -1.0

def convert_to_mib(s):
    if 'MiB' in s:
        return float(s.replace('MiB', ''))
    else:
        return -1.0

title = sys.argv[1]

df = pd.read_csv(f'stats-{title}.csv')

time0 = list(df['time'])[0]
df['time'] = df['time'].apply(lambda i: i - time0)
df['CPU'] = df['cpu_perc'].apply(lambda s: float(s.replace('%', '')))
df['Network In'] = df['net_io'].apply(lambda s: convert_to_mb(s.split(' / ')[0]))
df['Network Out'] = df['net_io'].apply(lambda s: convert_to_mb(s.split(' / ')[1]))
df['Memory'] = df['mem_usage'].apply(lambda s: convert_to_mib(s.split(' / ')[0]))

fig, axes = plt.subplots(nrows=1, ncols=3, figsize=(20, 5))
plt.suptitle(title)

df.plot(title='CPU usage', ax=axes[0], x='time', y='CPU', xlabel='time (seconds)', ylabel='CPU usage (percent)', grid=True)
df.plot(title='network usage', ax=axes[1], x='time', y=['Network In', 'Network Out'], xlabel='time (seconds)', ylabel='Network I/O (MB)', grid=True)
df.plot(title='memory usage', ax=axes[2], x='time', y=['Memory'], xlabel='time (seconds)', ylabel='Memory usage (MiB)', grid=True)
plt.savefig(f'stats-{title}.png')
