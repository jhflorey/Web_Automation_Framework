from django.shortcuts import render, HttpResponse
import random 


def index(request):
    i = datetime.datetime.now()
    currentDateTime = ("%s/%s/%s" % (i.day,i.month,i.year)) + (" %s:%s:%s" % (i.hour,i.month,i.second))
    print currentDateTime

    # context={
    # "currentDateTime":currentDateTime
    # }
    context = {
    "somekey":currentDateTime 
    }
    return render(request, 'timedisplay/index.html', context)

def yourMethodFromUrls(request):
	context = {
	"somekey":"somevalue"
	}
	return render(request,'appname/page.html', context)

# Create your views here.
