require 'sinatra'

configure do
  set :hits, Hash.new
end

get '/hit/:id' do |id|
  settings.hits[id] = Time.now.to_i.to_s
end

get '/check/:id' do |id|
  settings.hits[id] or 'Fully Alive'
end
