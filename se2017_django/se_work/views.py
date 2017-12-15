from django.http import HttpResponse
from django.shortcuts import render, redirect
from pymongo.auth import authenticate
from .models import *


def index(request):
    global login_msg
    return render(request, 'index.html',{'login_msg':login_msg})


def login(request):
    return render(request, 'login.html', {'login_msg':login_msg})


def shop(request):
    global login_msg
    return render(request, 'shop.html', {'login_msg':login_msg})
    #return HttpResponse(request.session['user_id']+"是帅哥")


def my_account(request):
    global login_msg
    return render(request, 'my-account.html', {'login_msg':login_msg})


def about(request):
    global login_msg
    return render(request, 'about.html', {'login_msg':login_msg})


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
        return render(request, 'index.html',)


def register_fun(request):
    global login_msg
    if 're_username' in request.POST and 're_password'in request.POST and 're_confirm_password'in request.POST:
        if request.POST['re_password'] != request.POST['re_confirm_password']:
            return HttpResponse("两次密码不一致")
        else:
            user_registed = Customer(_id=request.POST['re_username'], password=request.POST['re_password'], sex=request.POST['sex'])
            user_registed.save()
    return render(request, 'index.html',)


def add(request):
    a = request.GET.get('a',0)
    b = request.GET.get('b',0)
    c = int(a)+int(b)
    return HttpResponse(str(c))


def add2(request, a, b):
    c = int(a) + int(b)
    return HttpResponse(str(c))


login_msg = "登录 或 注册"

# choice1 = Choice(choice_text="just test django", votes="1")
# poll1 = Poll(question="")
# Create your views here.
