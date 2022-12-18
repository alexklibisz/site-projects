import pandas as pd

def convert_to_mb(s):
    if 'kB' in s:
        return float(s.replace('kB', '')) * 0.001
    elif 'MB' in s:
        return float(s.replace('MB', ''))
    else:
        return -1.0

df = pd.read_csv('stats.csv')

time0 = df['time'][0]
df['time'] = df['time'].apply(lambda i: i - time0)
df['cpu_perc'] = df['cpu_perc'].apply(lambda s: float(s.replace('%', '')))
df['net_in_mb'] = df['net_io'].apply(lambda s: convert_to_mb(s.split(' / ')[0]))
df['net_out_mb'] = df['net_io'].apply(lambda s: convert_to_mb(s.split(' / ')[1]))

ax = df.plot(x='time', y='cpu_perc', xlabel='time (seconds)', ylabel='CPU usage (percent)', grid=True)
ax.figure.savefig('stats-cpu.png')

ax = df.plot(x='time', y=['net_in_mb', 'net_out_mb'], xlabel='time (seconds)', ylabel='Network I/O (MB)', grid=True)
ax.figure.savefig('stats-network.png')
