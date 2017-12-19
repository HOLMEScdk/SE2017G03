from django.http import HttpResponse
from django.shortcuts import render, redirect
from pymongo.auth import authenticate
from .models import *


def index(request):
    global login_msg
    return render(request, 'index.html', {'login_msg':login_msg})


def product(request):
    global login_msg
    return render(request, 'product.html', {'login_msg': login_msg})
    #return render(request, 'product.html',{'login_msg':login_msg})


def product_ref(request, product_id):
    global login_msg
    products = Products.objects(name=product_id)
    product_ = products[0]
    return render(request, 'product_ref.html', {'product': product_, 'login_msg':login_msg})


def login(request):
    return render(request, 'login.html', {'login_msg': login_msg})


def shop(request):
    global login_msg
    products = Products.objects()
    return render(request, 'shop.html', {'login_msg': login_msg, 'products': products})


def my_account(request):
    has_address = False
    global login_msg
    if 'user_id' in request.session.keys():
        customer = Customer.objects(_id=request.session['user_id'])[0]
        info_address = customer.extra_info
        # print("HE------------", has_address, len(info_address), len(info_address) > 0)
        if len(info_address) > 0:
            has_address = True
        return render(request, 'my-account.html', {'login_msg': login_msg,
                                               'info_address': info_address,
                                               'has_address': has_address,
                                               })
    else:
        return render(request, 'my-account.html', {'login_msg':login_msg})


def address_fun(request):
    global login_msg
    if 'user_id' in request.session.keys() and 'name_address' in request.POST and 'address_address' in request.POST and 'phone_address' in request.POST:
        user_name_address = AddExtraInfo(name=request.POST['name_address'], address=request.POST['address_address'],
                                         phone=request.POST['phone_address'])
        customer = Customer.objects(_id=request.session['user_id']).first()
        customer.extra_info.append(user_name_address)
        customer.save()
        info_address = customer.extra_info
        # print("HE------------", has_address, len(info_address), len(info_address) > 0)
        has_address = True
    return render(request, 'my-account.html', {'login_msg': login_msg,
                                               'info_address': info_address,
                                               'has_address': has_address,
                                               })


def about(request):
    global login_msg
    return render(request, 'about.html', {'login_msg': login_msg})


def login_fun(request):
    global login_msg
    if 'username' in request.POST and 'password' in request.POST:
        user_logined = Customer.objects(_id=request.POST["username"])[0]
        if request.POST['password'] == user_logined.password:
            request.session['user_id'] = user_logined._id          #  从session中获取的id永远是本次登录者的id。
            login_msg = '欢迎:' + request.session['user_id']
            return render(request, 'index.html', {'login_msg': login_msg})
        else:
            return HttpResponse(request.GET['username']+"是不正确账号")
    else:
        return render(request, 'index.html',{'login_msg' : login_msg})


def register_fun(request):
    global login_msg
    if 're_username' in request.POST and 're_password'in request.POST and 're_confirm_password'in request.POST:
        if request.POST['re_password'] != request.POST['re_confirm_password']:
            return HttpResponse("两次密码不一致")
        else:
            user_registed = Customer(_id=request.POST['re_username'], password=request.POST['re_password'], sex=request.POST['sex'])
            user_registed.save()
            request.session['user_id'] = user_registed._id
            login_msg = '欢迎:' + request.session['user_id']
    return render(request, 'index.html', {'login_msg': login_msg})


def add_cart(request):
    global login_msg
    print("HERE-----------")
    if 'user_id' in request.session.keys():
        pro = Products.objects(_id=request.POST['prod_id'])[0]
        cart = Carts(product_id=pro.id,
                     product_name=pro.name,
                     size="XL",
                     price=pro.sale_price,
                     color="红",
                     amount=pro.amount)
        customer = Customer.objects(_id=request.session['user_id'])[0]
        customer.carts.append(cart)
        customer.save()
        html = "product/" + pro.name + ".html"
        return render(request, html, {'login_msg': login_msg})
    return HttpResponse("请先登录账号")


def search_result(request, key):
    global login_msg
    search_res = False
    product_res_first = Products.objects(name__contains=key)
    product_res_second = Products.objects(description__contains=key)
    # if product_res_first.first() is not None and product_res_second.first() is not None:
    #     search_res = True
    if product_res_first.first() is not None or product_res_second.first() is not None:
        search_res = True
    return render(request, 'search_result.html', {'login_msg': login_msg,
                                                  'product_res_first': product_res_first,
                                                  'product_res_second': product_res_second,
                                                  'search_res': search_res})
login_msg = "登录 或 注册"
# choice1 = Choice(choice_text="just test django", votes="1")
# poll1 = Poll(question="")
# Create your views here.
