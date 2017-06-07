print "Hello World!"
my_string = "This is a string stored in the my_string variable"
my_num = 5 # an integer stored in a variable
my_tuple = (1,2,3,4,5) # a tuple stored in a variable
# a dictionary stored in a variable
my_dictionariy = {'name': 'Michael Choi', 'fave_lang': 'Python', 'level': 'Sensei'}
# define a function that says hello to the name provided
# this starts a new block
def say_hello(name):
	#these lines are indented therefore part of the function
	if name:
		print 'Hello, ' + name + 'from inside the function'
	else:
		print 'No name'
# now we're unindented and have ended the previous block
print 'Outside of the function'
# create variable called greeting that holds the value of a string
greetings = "Hello Ninja!"
print greetings
# you can use single or double quotes for strings
print 'What is your name?'
# we use raw_input()to get user input and set it to a variable
name = raw_input()
print "How old are you?"
# we can also use input() to get user input
age = input()
#adding of variables to a string to be printed is like this:
print "Your name is", name
print "You are", age, "year old"
# you can also add the variable in between strings just like the above
raw_input("\n\nPress the enter key to exist.")
# the line above would make the app not close or exit out automatically.
print "this is a sample string"
name = "Zen"
print "My name is", name
name = "Zen"
print "My name is" + name
first_name = "Zen"
last_name = "Coder"
print "My name is {} {}".format(first_name, last_name)
