"""se2017_django URL Configuration

The `urlpatterns` list routes URLs to views. For more information please see:
    https://docs.djangoproject.com/en/1.11/topics/http/urls/
Examples:
Function views
    1. Add an import:  from my_app import views
    2. Add a URL to urlpatterns:  url(r'^$', views.home, name='home')
Class-based views
    1. Add an import:  from other_app.views import Home
    2. Add a URL to urlpatterns:  url(r'^$', Home.as_view(), name='home')
Including another URLconf
    1. Import the include() function: from django.conf.urls import url, include
    2. Add a URL to urlpatterns:  url(r'^blog/', include('blog.urls'))
"""
from django.conf.urls import url
from django.contrib import admin
from se_work import views as se_views
from django.conf import settings
urlpatterns = [
    url(r'^$',se_views.index, name='home'),#use some api to provide a view
    url(r'index.html$',se_views.index, name='home'),
    url(r'shop.html$',se_views.shop, name='shop'),
    url(r'my-account.html$', se_views.my_account, name='my_account'),
    url(r'about.html$', se_views.about, name='about'),
    url(r'login.html$', se_views.login, name='login'),
    url(r'add$', se_views.add, name='add'),
    url(r'^add/(\d+)/(\d+)/$', se_views.add2, name='add2'),
    url(r'^admin/', admin.site.urls),
    url(r'^login_fun$', se_views.login_fun),
    url(r'^register_fun$', se_views.register_fun),
]
