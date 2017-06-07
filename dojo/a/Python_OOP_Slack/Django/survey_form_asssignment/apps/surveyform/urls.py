from django.conf.urls import url, include
# from django.contrib import admin
from .import views


urlpatterns = [
    url(r'^$',views.index),
    url(r'^surveyform$', views.surveyform),
    url(r'^show_users$', views.show_users),
]
