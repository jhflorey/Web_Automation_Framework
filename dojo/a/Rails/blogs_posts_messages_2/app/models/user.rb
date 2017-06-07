class User < ActiveRecord::Base
	EMAIL_REGEX = /\A([^@\s]+)@((?:[-a-z0-9]+\.)+[a-z]+)\z/i
  	has_many :owners
  	has_many :messages
 	has_many :posts
  	has_many :blogs, through: :owners

  	validates :email_address, format: { with: EMAIL_REGEX }
  	validates :first_name, :last_name, :email_address, presence: true
end
