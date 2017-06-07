class Commentable < ActiveRecord::Migration[5.0]
  def change
  	add_column :users, :commentable, :string
  	add_column :blogs, :commentable, :string
  	add_column :posts, :commentable, :string
  	add_column :messages, :commentable, :string
  end
end
