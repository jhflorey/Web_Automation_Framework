from django.shortcuts import render, HttpResponse, redirect

# Create your views here.
def index(request):
	return render(request, 'surveyform/index.html')

def surveyform(request):
	if 'counter' in request.session:
		request.session['counter'] += 1
	else:
		request.session['counter'] = 1

	request.session['data'] = {
	'Name': request.POST['name'],
	'Dojo Location': request.POST['dojolocation'],
	'Favorite Language': request.POST['favoritelanguage'],
	'Comments (Optional)': request.POST['comment'],
	}
	return redirect('/show_users')

def show_users(request):
	print 'Go to show users!'
	print request.session
	return render(request, 'surveyform/show_users.html')

def runThis():
	return redirect('/')