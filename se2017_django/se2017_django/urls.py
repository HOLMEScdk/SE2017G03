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
from django.conf import settings
from django.conf.urls.static import static

urlpatterns = [
    url(r'^$',se_views.index, name='home'),#use some api to provide a view
    url(r'index.html$', se_views.index, name='home'),
    url(r'shop.html$', se_views.shop, name='shop'),
    url(r'product.html$', se_views.product, name='product'),
    url(r'cart.html$', se_views.cart, name='cart'),
    #url(r'product_ref.html$', se_views.product_ref, name='product_ref'),
    url(r'product_ref/(?P<product_id>\w+)/', se_views.product_ref, name='product_ref'),
    url(r'my-account.html$', se_views.my_account, name='my_account'),
    url(r'about.html$', se_views.about, name='about'),
    url(r'login.html$', se_views.login, name='login'),
    url(r'^admin/', admin.site.urls),
    url(r'^login_fun$', se_views.login_fun),
    url(r'^register_fun$', se_views.register_fun),
    url(r'^address_fun$', se_views.address_fun),
    url(r'^product_ref/(?P<product_id>\w+)t/$', se_views.add_cart),
    url(r'^add_cart$', se_views.add_cart),
    url(r'search_result&&input_key=(?P<key>\w+)/', se_views.search_result, name='search_result'),
    url(r'^ajax_add_cars$', se_views.ajax_add_cars),
] + static(settings.MEDIA_URL, document_root=settings.MEDIA_ROOT)
