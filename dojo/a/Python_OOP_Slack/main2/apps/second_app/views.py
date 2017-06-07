from django.shortcuts import render


def index(request):
	context = {
		'email': 'blob@gmail.com',
		'name': 'jessica'
	}
	return render(request, 'second_app/index.html', context)

def show(request, id): 
	context = {
		'id': id	
	}
	return render(request, 'second_app/show.html', context)