import sqlite3
import pandas as pd
database = sqlite3.connect('Soccer.sqlite')
Player_Names = pd.read_sql_query("SELECT * from Player",database)
Player_Attributes = pd.read_sql_query("SELECT * from Player_Attributes",database)
Player_ID = pd.merge(Player_Names,Player_Attributes,on=['player_api_id'],how='outer')
#print(player_name_and_id.head(3))
average_rating_for_each_player = Player_ID.groupby(['player_name','player_api_id','player_fifa_api_id_x']).mean().reset_index()
#print(average_rating_for_each_player.head(3))

#Calculating the top 20 players
Skills = ['overall_rating','potential', 'crossing', 'finishing', 'heading_accuracy', 'short_passing', 'volleys', 'dribbling', 'curve', 'free_kick_accuracy','long_passing', 'ball_control', 'acceleration', 'sprint_speed','agility', 'reactions', 'balance', 'shot_power', 'jumping', 'stamina','strength', 'long_shots', 'aggression', 'interceptions', 'positioning','vision', 'penalties', 'marking', 'standing_tackle', 'sliding_tackle','gk_diving', 'gk_handling', 'gk_kicking', 'gk_positioning','gk_reflexes']
top_20 = []
for attribute in Skills:
    score = average_rating_for_each_player.nlargest(20,attribute)[['player_name',attribute]]
    score  = score.rename(columns={score.columns[1]:'Average Score'})
    score = score.rename(columns={score.columns[0]: 'Name of the Player'})
    top_20.append(score)

top_20df = pd.concat(top_20, keys = Skills)
#Printing top 20 Players
print(top_20df.ix['overall_rating'])