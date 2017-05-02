import numpy as np
import matplotlib.pyplot as plt
import pandas as pd
import sqlite3
import numpy as np
from numpy import random

with sqlite3.connect('Soccer.sqlite') as con:
    countries = pd.read_sql_query("SELECT * from Country", con)
    matches = pd.read_sql_query("SELECT * from Match", con)
    leagues = pd.read_sql_query("SELECT * from League", con)
    teams = pd.read_sql_query("SELECT * from Team", con)

selected_countries = ['England','France','Germany','Italy','Spain']
countries = countries[countries.name.isin(selected_countries)]
leagues = countries.merge(leagues,on='id',suffixes=('', '_y'))
matches = matches[matches.league_id.isin(leagues.id)]
matches = matches[['id', 'country_id' ,'league_id', 'season', 'stage', 'date','match_api_id', 'home_team_api_id', 'away_team_api_id','B365H', 'B365D' ,'B365A']]
matches.dropna(inplace=True)

from scipy.stats import entropy
def match_entropy(row):
    odds = [row['B365H'],row['B365D'],row['B365A']]
    probs = [1/o for o in odds]
    norm = sum(probs)
    probs = [p/norm for p in probs]
    return entropy(probs)

matches['entropy'] = matches.apply(match_entropy,axis=1)
entropy_means = matches.groupby(('season','league_id')).entropy.mean()
entropy_means = entropy_means.reset_index().pivot(index='season', columns='league_id', values='entropy')
entropy_means.columns = [leagues[leagues.id==x].name.values[0] for x in entropy_means.columns]
#Printing Entropy
#print(entropy_means.head(10))

plt.interactive(False)
Graph = entropy_means.plot(figsize=(12,12),marker='o')
plt.title('Predictability of the Leagues', fontsize=20)
plt.xticks(rotation=50)
colors = [x.get_color() for x in Graph.get_lines()]
colors_mapping = dict(zip(leagues.id,colors))
Graph.set_xlabel('Dates', fontsize=18)
plt.legend(loc='lower left')
#add arrows
Graph.annotate('', xytext=(7.3, 1),xy=(7.2, 1.039),arrowprops=dict(facecolor='black',arrowstyle="->, head_length=.7, head_width=.3",linewidth=1), annotation_clip=False)
Graph.annotate('', xytext=(7.3, 0.96),xy=(7.2, 0.921),arrowprops=dict(facecolor='black',arrowstyle="->, head_length=.7, head_width=.3",linewidth=1), annotation_clip=False)
Graph.annotate('Predictability - Less', xy=(7.6, 1.028), annotation_clip=False,fontsize=14,rotation='vertical')
Graph.annotate('Predictability - More', xy=(7.6, 0.952), annotation_clip=False,fontsize=14,rotation='vertical')
plt.show()
