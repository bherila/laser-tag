require 'sinatra'

configure do
  deaths = Hash.new {|h,k| h[k] = 0 }
  set :deaths, deaths
end

get '/hit/:id/:damage' do |id, damage|
  settings.deaths[id] += 1
  'OK'
end

get '/check/:id' do |id|
  settings.deaths[id].to_s
end
