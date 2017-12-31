from django.http import HttpResponse
from django.shortcuts import render
from .models import *
import json
from django.views.decorators.csrf import csrf_exempt
@csrf_exempt


def index(request):
    products = Products.objects()
    return render(request, 'index.html', {'login_msg': '欢迎:' + request.session['user_id'] if 'user_id' in request.session.keys() else  "登录 / 注册",
                                          'products': products
                                          })


def product(request):
    if 'user_id' in request.session.keys():
        return render(request, 'product.html', {'login_msg': '欢迎:' + request.session['user_id']})
    else:
        return render(request, 'product.html', {'login_msg': '登录 / 注册'})
    #return render(request, 'product.html',{'login_msg':login_msg})


def product_ref(request, product_id):
    products = Products.objects(name=product_id)
    product_ = products[0]
    if 'user_id' in request.session.keys():
        return render(request, 'product_ref.html', {'product': product_,
                                                    'login_msg': '欢迎:' + request.session['user_id']})
    else:
        return render(request, 'product_ref.html', {'product': product_,
                                                    'login_msg': "登录 / 注册"})


def login(request):
    if 'user_id' in request.session.keys():
        return render(request, 'login.html', {'login_msg': '欢迎:' + request.session['user_id']})
    else:
        return render(request, 'login.html', {'login_msg': "登录 / 注册"})


def shop(request):
    products = Products.objects()
    if 'user_id' in request.session.keys():
        return render(request, 'shop.html', {'login_msg': '欢迎:' + request.session['user_id'], 'products': products})
    else:
        return render(request, 'shop.html', {'login_msg': '登录 / 注册', 'products':products})


# 可以显示我的账户总的地址信息
def my_account(request):
    has_address = False
    if 'user_id' in request.session.keys():
        customer = Customer.objects(_id=request.session['user_id'])[0]
        info_address, orders = customer.extra_info, customer.order
        # print("HE------------", has_address, len(info_address), len(info_address) > 0)
        if len(info_address) > 0:
            has_address = True
        return render(request, 'my-account.html', {'login_msg': '欢迎:' + request.session['user_id'],
                                                   'info_address': info_address,
                                                   'has_address': has_address,
                                                    'orders': orders,
                                                   })
    else:
        return render(request, 'my-account.html', {'login_msg': '登录 / 注册'})


def cart(request):
    if 'user_id' in request.session.keys():
        customer = Customer.objects(_id=request.session['user_id']).first()
        carts = customer.carts
        pay_total = 0
        for each in carts:
            pay_total = pay_total + each.total_money
        return render(request, 'cart.html', {'login_msg': '欢迎:' + request.session['user_id'],
                                             'carts': carts,
                                             'pay_total': pay_total})
    else:
        return render(request, 'login.html', {'login_msg': '登录 / 注册'})


def about(request):
    if 'user_id' in request.session.keys():
        return render(request, 'about.html', {'login_msg': '欢迎:' + request.session['user_id']})
    else:
        return render(request, 'about.html', {'login_msg': '登录 / 注册'})


def login_fun(request):
    products = Products.objects()

    if request.method == 'POST':
        try:
            user_logined = Customer.objects(_id=request.POST.get("username"))[0]
        except:
            return HttpResponse("该账号不存在")
        if request.POST.get('password') == user_logined.password:
            request.session['user_id'] = user_logined._id    #  从session中获取的id永远是本次登录者的id。
            # login_msg = '欢迎:' + request.session['user_id']
            return HttpResponse('登录成功')
        else:
            return HttpResponse("密码错误")
    else:
        return HttpResponse('登录失败')


def logout(request):
    if 'user_id' in request.session.keys():
        del request.session['user_id']
        return render(request, 'login.html', {'login_msg': "登录 / 注册"})
    else:
        return HttpResponse("请先登录!")


def change_own_password(request):
    mes = "连接失败"
    if request.method == "POST":
        # print("*"* 10)
        # print(request.session['user_id'])
        customer = Customer.objects(_id=request.session['user_id']).first()
        origin = request.POST.get('passwd')
        new1 = request.POST.get('new1_passwd')
        new2 = request.POST.get('new2_passwd')
        if len(new1) == 0 or len(new2) == 0:
            return HttpResponse("新密码不能为空")
        if origin != customer.password:
            return HttpResponse('原密码输入错误')
        elif new1 != new2:
            return HttpResponse('两次输入密码不一致')
        else:
            customer.password = str(request.POST.get('new1_passwd'))
            customer.save()
            # print("THIRD")
    return HttpResponse('密码修改成功')


def change_password(request):
    if 'user_id' in request.session.keys():
        return render(request, 'changed_password.html', {'login_msg': '欢迎:' + request.session['user_id']})
    else:
        return render(request, 'login.html', {'login_msg': "登录 / 注册"})


def register_fun(request):
    # products = Products.objects()
    if request.method == "POST":
        user_id = request.POST.get('re_username')
        user_passwd = request.POST.get('re_password')
        user_confirm_passwd = request.POST.get('re_confirm_password')
        user_sex = request.POST.get('sex')
        if len(user_id) == 0:
            return HttpResponse('账号不能为空')
        elif user_passwd != user_confirm_passwd:
            return HttpResponse("两次密码不一致")
        elif len(user_passwd) == 0 or len(user_confirm_passwd) == 0:
            return HttpResponse('密码不能为空')
        elif len(user_id)> 15:
            return HttpResponse('账号过长，请不要超过15位')
        elif len(user_passwd) > 15:
            return HttpResponse('密码过长，请不要超过15位')
        elif len(Customer.objects(_id=user_id)) != 0:
            return HttpResponse('账户已存在')
        else:
            user_registed = Customer(_id=user_id, password=user_passwd, sex=user_sex)
            user_registed.save()
            request.session['user_id'] = user_registed._id
            # login_msg = '欢迎:' + request.session['user_id']
    return HttpResponse('成功注册')
    #return render(request, 'index.html', {'login_msg': '欢迎:' + request.session['user_id'],'products':products})


def ajax_add_cars(request):
    username = request.session.get('username', None)
    if username == '':
        return render(request, 'login.html')
    if request.method == "POST":
        value = request.POST.get("id")
        pro = Products.objects(_id=value)[0]
        amount = request.POST.get("amount")
        amount_ca = int(amount)
        total_money = amount_ca * pro.sale_price
        #print("********************************************************************"+product.name )
        cart = Carts(product_id=pro.id,
                     product_name=pro.name,
                     size="XL",
                     price=pro.sale_price,
                     color="红",
                     amount=amount,
                     total_money=total_money,
                     src=pro.src)
        try:
          customer = Customer.objects(_id=request.session['user_id'])[0]
          customer.carts.append(cart)
          customer.save()
        except:
            return render(request, 'login.html')
    return HttpResponse(500)


def search_result(request, key):
    search_res = False
    product_res_first = Products.objects(name__contains=key)
    product_res_second = Products.objects(description__contains=key)
    # if product_res_first.first() is not None and product_res_second.first() is not None:
    #     search_res = True
    if product_res_first.first() is not None or product_res_second.first() is not None:
        search_res = True
    return render(request, 'search_result.html',
                  {'login_msg': '欢迎:' + request.session['user_id'] if 'user_id' in request.session.keys() else  "登录 / 注册",
                   'product_res_first': product_res_first,
                   'product_res_second': product_res_second,
                   'search_res': search_res})


def checkout(request):
    global login_msg
    if 'user_id' in request.session.keys():
        customer_ = Customer.objects(_id=request.session['user_id'])
        customer = customer_[0]
        carts = customer.carts
        p_name = []
        p_color = []
        p_amount = []
        p_price = []
        p_size = []
        summ = 0
        # 从cart里面获取的商品信息
        for each in carts:
            p_name.append(each.product_name)
            p_color.append(each.color)
            p_amount.append(each.amount)
            p_price.append(each.price)
            p_size.append(each.size)
            summ += each.amount * each.price
        order_ = Order(product_name=p_name, color=p_color, size=p_size,
                       single_price=p_price, amount=p_amount, total_money=summ)

        # for each in carts:
        #    customer_.update_one(pull__carts=each)
        customer_.update(unset__carts=1)
        order_.save()
        order_id = order_.id
        customer.order.append(order_)
        customer.save()
        return render(request, 'checkout.html', {'orderid': order_id})
    return render(request, 'login.html', {'login_msg': "登录 / 注册"})


def address_fun(request):
    global mess
    new_address = {}
    has_address = False
    if 'user_id' in request.session.keys():
        if len(request.POST.get('name_address')) > 10:
            mess = "请输入一个长度在10位以内且有效的名字"
            return HttpResponse(json.dumps({'mess': mess, 'new_address': new_address, 'has_address': has_address}))
        elif len(request.POST.get('name_address')) == 0:
            mess = "请不要输入一个空姓名"
            return HttpResponse(json.dumps({'mess': mess, 'new_address': new_address, 'has_address': has_address}))
        elif len(request.POST.get('address_address')) > 30:
            mess = "请输入一个长度在30位以内且有效的地址"
            return HttpResponse(json.dumps({'mess': mess, 'new_address': new_address, 'has_address': has_address}))
        elif len(request.POST.get('address_address')) == 0:
            mess = "请不要输入空地址"
            return HttpResponse(json.dumps({'mess': mess, 'new_address': new_address, 'has_address': has_address}))
        elif len(request.POST.get('phone_address')) != 11:
            mess = "请输入一个长度为11位且有效的电话号码"
            return HttpResponse(json.dumps({'mess': mess, 'new_address': new_address, 'has_address': has_address}))
        else:
            customer = Customer.objects(_id=request.session['user_id']).first()
            rank = 0
            for each in customer.extra_info:
                if type(each.rank) == int and each.rank >= rank:
                    rank = each.rank + 1
            user_name_address = AddExtraInfo(rank=rank, name=request.POST.get('name_address'), address=request.POST.get('address_address'),
                                             phone=request.POST.get('phone_address'))
            new_address['address'] = user_name_address.address
            new_address['name'] = user_name_address.name
            new_address['phone'] = user_name_address.phone
            new_address['rank'] = user_name_address.rank
            customer.extra_info.append(user_name_address)
            customer.save()
            # info_address = customer.extra_info
            has_address = True
            mess = '成功添加地址'
            return HttpResponse(json.dumps({'mess': mess, 'new_address': new_address, 'has_address': has_address}))
        # return render(request, 'my-account.html', {'login_msg': '欢迎:' + request.session['user_id'],
        #                                            'info_address': info_address,
        #                                            'has_address': has_address,
        #                                            })
    else:
        mess = "请先登录"
        return HttpResponse(json.dumps({'mess': mess, 'new_address': new_address, 'has_address': has_address}))


def delete_product_cart(request):
    if request.method == 'POST':
        p_id = request.POST.get("id")  # 得到购物车中该商品id
        print(p_id)
        # print("-------------IDID",id)
        Customer.objects(_id=request.session['user_id']).update_one(pull__carts__product_id=p_id)
        customer = Customer.objects(_id=request.session['user_id']).first()
        carts = customer.carts
        pay_total = 0
        for each in carts:
            pay_total = pay_total + each.total_money
        # carts = serializers.serialize("json",Customer.objects(_id=request.session['user_id'])[0].carts)
    return HttpResponse(json.dumps({"id": p_id, "price": pay_total}))


def delete_address(request):
    if request.method == 'POST':
        rank = int(request.POST.get("rank"))  # 得到购物车中该商品id
        # print("-------------IDID",id)
        Customer.objects(_id=request.session['user_id']).update_one(pull__extra_info__rank=rank)
        print(rank)
    return HttpResponse(json.dumps({"rank": rank}))

'''

def add_cart(request):
    global login_msg
    print("HERE-----------")
    if 'user_id' in request.session.keys():
        pro = Products.objects(_id=request.POST['prod_id'])[0]
        total_money = 0
        total_money += float(pro.amount) * float(pro.sale_price)
        print("**************************************************",total_money)
        cart_instance = Carts(product_id=pro.id,
                              product_name=pro.name,
                              size="XL",
                              price=pro.sale_price,
                              color="红",
                              amount=pro.amount,
                              total_money=total_money,
                              src=pro.src
                              )
        customer = Customer.objects(_id=request.session['user_id'])[0]
        customer.carts.append(cart_instance)
        customer.save()
        html = "product/" + pro.name + ".html"
        return render(request, html, {'login_msg': '欢迎:' + request.session['user_id']})
    return HttpResponse("请先登录账号")
'''


login_msg = "登录 / 注册"
# choice1 = Choice(choice_text="just test django", votes="1")
# poll1 = Poll(question="")
# Create your views here.
