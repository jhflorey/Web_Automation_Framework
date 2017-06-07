from __future__ import unicode_literals

from django.db import models


# Create your models here.
class Blog(models.Model):
	title = models.CharField(max_length=100)
	blog = models.TextField(max_length=1000)
	created_at = models.DateTimeField(auto_now_add = True)
	updated_at = models.DateTimeField(auto_now = True)


class Comment(models.Model):
	comment = models.TextField(max_length=1000)
	blog = models.ForeignKey(Blog)
	created_at = models.DateTimeField(auto_now_add=True)
	updated_at = models.DateTimeField(auto_now=True)


class User(models.Model):
	first_name = models.CharField(max_length=45)
	last_name = models.CharField(max_length=45)
	age = models.IntegerField()
	created_at = models.DateTimeField(auto_now_add=True)
	updated_at = models.DateTimeField(auto_now=True)
	def __str__(self):
		return self.first_name + " " + self.last_name


	# GET : if we want the oe user with last name "Thomas", we could say:
	user = User.objects.get(last_name='Thomas')
	print ('QUERY RESULT:', user)

	# only use .get if you know that there's only one matching item
	user = User.objects.get(id=6)
	print ('Query Result:', user)

	# FILTER : return all of the records where a given condition is true.
	user = User.objects.filter(last_name='Thomas')
	print ('Query result:', user)

	# Exclude is the opposite of .filter : It return allmod the recocds where a given codition is false
	user = User.objects.exclude(last_name='Thomas')
	print ('Query Result:', user)

	# conditions : to find users whose first name begins with "s"
	user = User.objects.filter(first_name__startswith=='S')
	print ('Query Result:', user)

	user = User.objects.exclude(first_name__contains="S")
	print ('Query Result:', user)

	# every use 80 or older age
	user = User.objects.filter(age__gte=80)
	print('Query Result:', user)

	# combine queries:
	user = User.objects.filter(last_name__contains='o').exclude(first_name__contains='o')
	print ('Query Result:', user)

	user = User.objects.filter(age__lt=70).filter(first_name__startswith='S')
	print("Query Result:", user)

	# if it's the same type of query, instead of being chained you can multiple arguments to the function:
	user = User.objects.filter(age__lt=70, first_name__startswith="S")
	print ("Query Result:", user)

	# these are case where conditions are joined with AND, as in, all users yonger than 70 AND whose first name 
	# start with "S". If you want OR, as in users who are yonger than 70 OR whose first_name starts with "S", you 
	# can use | (the set union operator):
	user = User.objects.filter(age__ls=70)|User.objects.filter(first_name__startswith="S")
	print("Query Result:", user)


	# Display on templates:
	def index(request):
		users = User.objects.filter(age__ls=70)|User.objects.filter(first_name__startswith="S")
		context = {"users": users}
		return render(request, "users/index.html", context)
		





















