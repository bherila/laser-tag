require 'sinatra'

configure do
  set :hits, Hash.new
  set :HP, Hash.new # change to int
end

get '/hit/:id/:range' do |id|
  # settings.hits[id] = Time.now.to_i.to_s
  settings.HP[id] -= range
  if settings.HP[id] <= 0
    'Target dead'
  else
    'Target alive'
  end
end

get '/check/:id' do |id|
  # settings.hits[id] or 'Fully Alive'
  if settings.HP[id] <= 0
    'Dead'
  else
    'Alive'
  end
end
