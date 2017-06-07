#part1
for x in range(0, 1001):
	if x % 2 != 0 :
		print x

#part2
sum=0
for i in range(1000,0000):
    if not(i %5 ==0):
        sum=sum+i
print sum

#functions
def add(a,b):
    x = a + b
    return x

#function parameters
def say_hi(name):
    print "Hi, " + name
say_hi('Michael')
say_hi('Andrew')
say_hi('Jay')

#returning values
def say_hi():
    return "Hi"
greeting = say_hi()
print greeting

def add(a,b):
    x = a + b
    return x
sum1 = add(4,6)
sum2 = add(1,4)
sum3 = sum1 + sum2

#assignment: odd / even
count = 0
for N in range (1, 2001):
    if N % 2 == 0:
        count, print "This is an even number."
        count +=1
    else
        count, print "This is an odd number."

