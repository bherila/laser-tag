require 'sinatra'

configure do
  set :hits, Hash.new

  deaths = Hash.new {|h,k| h[k]=0}
  set :deaths, deaths

  # hp = Hash.new {|h,k| h[k] = 100}
  # set :HP, hp
end

get '/hit/:id/:damage' do |id, damage|
  settings.hits[id] = Time.now.to_i.to_s
  settings.deaths[id] += 1
  'OK'
  # settings.HP[id] = settings.HP[id] - damage.to_i
  # if settings.HP[id] <= 0
  #   'Target dead'
  # else
  #   'Target alive'
  # end
end

get '/check/:id' do |id|
  settings.deaths[id].to_s
  # if settings.HP[id] <= 0
  #   settings.HP[id] = 100
  #   'Dead'
  # else
  #   'Alive'
  # end
end
