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

def munge_dataframe(df):
    time0 = list(df['time'])[0]
    df['time'] = df['time'].apply(lambda i: i - time0)
    df['CPU'] = df['cpu_perc'].apply(lambda s: float(s.replace('%', '')))
    df['Network In'] = df['net_io'].apply(lambda s: convert_to_mb(s.split(' / ')[0]))
    df['Network Out'] = df['net_io'].apply(lambda s: convert_to_mb(s.split(' / ')[1]))
    df['Memory'] = df['mem_usage'].apply(lambda s: convert_to_mib(s.split(' / ')[0]))    
    return df

def get_lim(s1, s2):
    return (
        min(min(s1), min(s2)) * 0.9,
        max(max(s1), max(s2)) * 1.1
    )

dff = munge_dataframe(pd.read_csv(f'stats-function.csv'))
dfq = munge_dataframe(pd.read_csv(f'stats-query.csv'))

fig, axes = plt.subplots(nrows=2, ncols=2, figsize=(20, 10))
plt.suptitle("Are Postgres functions faster than queries?\n\nhttps://alexklibisz.com/2022/12/18/are-postgres-functions-faster-than-queries.html")

axes[0][0].plot(dff['time'], dff['CPU'], label='Function')
axes[0][0].plot(dfq['time'], dfq['CPU'], label='Query')
axes[0][0].set_xlabel('Time (s)')
axes[0][0].set_ylabel('CPU Usage (%)')
axes[0][0].set_ylim(get_lim(dff['CPU'], dfq['CPU']))
axes[0][0].legend()
axes[0][0].grid(visible=True)
axes[0][0].set_title('CPU Usage')

axes[0][1].plot(dff['time'], dff['Memory'], label='Function')
axes[0][1].plot(dfq['time'], dfq['Memory'], label='Query')
axes[0][1].set_xlabel('Time (s)')
axes[0][1].set_ylabel('Memory Usage (MiB)')
axes[0][1].set_ylim(get_lim(dff['Memory'], dfq['Memory']))
axes[0][1].legend()
axes[0][1].grid(visible=True)
axes[0][1].set_title('Memory Usage')

axes[1][0].plot(dff['time'], dff['Network In'], label='Function')
axes[1][0].plot(dfq['time'], dfq['Network In'], label='Query')
axes[1][0].set_xlabel('Time (s)')
axes[1][0].set_ylabel('Network In (MB)')
axes[1][0].set_ylim(get_lim(dff['Network In'], dfq['Network In']))
axes[1][0].legend()
axes[1][0].grid(visible=True)
axes[1][0].set_title('Network In')

axes[1][1].plot(dff['time'], dff['Network Out'], label='Function')
axes[1][1].plot(dfq['time'], dfq['Network Out'], label='Query')
axes[1][1].set_xlabel('Time (s)')
axes[1][1].set_ylabel('Network Out (MB)')
axes[1][1].set_ylim(get_lim(dff['Network Out'], dfq['Network Out']))
axes[1][1].legend()
axes[1][1].grid(visible=True)
axes[1][1].set_title('Network Out')

plt.savefig(f'stats.png')
