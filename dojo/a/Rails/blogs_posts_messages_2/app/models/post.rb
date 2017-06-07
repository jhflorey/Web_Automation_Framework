class Post < ActiveRecord::Base
	has_many :message
  	belongs_to :blog
 	belongs_to :user

 	validates :title, :content, presence: true
end
