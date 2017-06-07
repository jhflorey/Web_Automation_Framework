from django.conf.urls import url
from . import views

urlpatterns = [
    url(r'^$', views.index, name = 'index'),
    url(r'^blogs$', views.blogs),
    url(r'^comments/(?P\d+)$',views.comments),
]
