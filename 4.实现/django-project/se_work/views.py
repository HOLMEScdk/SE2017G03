from django.http import HttpResponse
from django.shortcuts import render
from .models import *
import json
from django.views.decorators.csrf import csrf_exempt
import time
import datetime
from bson import ObjectId
@csrf_exempt



def index(request):
    products = Products.objects()
    login_mes = "登录 / 注册"
    if 'user_id' in request.session.keys():
        login_mes = "欢迎:" + request.session['user_id']
    return render(request, 'index.html', {'login_msg': login_mes,
                                          'products': products
                                          })


def product(request):
    if 'user_id' in request.session.keys():
        login_mes = "欢迎:" + request.session['user_id']
        return render(request, 'product.html', {'login_msg': login_mes})
    else:
        return render(request, 'product.html', {'login_msg': '登录 / 注册'})


def product_ref(request, product_id):
    products = Products.objects(name=product_id)
    product_ = products[0]

    if 'user_id' in request.session.keys():
        login_mes = "欢迎:" + request.session['user_id']
        return render(request, 'product_ref.html', {'product': product_,
                                                    'login_msg': login_mes})
    else:
        return render(request, 'product_ref.html', {'product': product_,
                                                    'login_msg': "登录 / 注册"})


def login(request):
    if 'user_id' in request.session.keys():
        return my_account(request)
        # login_mes = "欢迎:" + request.session['user_id']
        # return render(request, 'login.html', {'login_msg': login_mes})
    else:
        return render(request, 'login.html', {'login_msg': "登录 / 注册"})


def shop(request):
    products = Products.objects()
    if 'user_id' in request.session.keys():
        login_mes = "欢迎:" + request.session['user_id']
        return render(request, 'shop.html', {'login_msg': login_mes, 'products': products})
    else:
        return render(request, 'shop.html', {'login_msg': '登录 / 注册', 'products':products})


# 可以显示个人信息总的地址信息
def my_account(request):
    has_address = False
    if 'user_id' in request.session.keys():
        customer = Customer.objects(_id=request.session['user_id'])[0]
        info_address, orders, red_rank = customer.extra_info, customer.order,customer.select_rank
        # print("HE------------", has_address, len(info_address), len(info_address) > 0)
        if len(info_address) > 0:
            has_address = True
        order_info = []
        for i, each in enumerate(orders):
            mapresult = {}
            mapresult['rank'] = i + 1
            mapresult['time'] = datetime.datetime.strftime(each.deal_time, '%Y-%m-%d %H%p')
            mapresult['status'] = each.status
            mapresult['money'] = each.total_money
            mapresult['id'] = str(each.id)
            # print(mapresult['id'])
            order_info.append(mapresult)
        login_mes = "欢迎:" + request.session['user_id']
        return render(request, 'my-account.html', {'login_msg': login_mes,
                                                   'info_address': info_address,
                                                   'has_address': has_address,
                                                    'orders': orders,
                                                   "red_rank": red_rank,
                                                   "order_info":order_info
                                                   })
    else:
        return render(request, 'my-account.html', {'login_msg': '登录 / 注册'})


def cart(request):
    order_allow = 1
    if 'user_id' in request.session.keys():
        customer = Customer.objects(_id=request.session['user_id']).first()
        carts = customer.carts
        red_rank = customer.select_rank
        exinfo = None
        all_amount = 0
        for item in customer.extra_info:
            if red_rank == item.rank:
                exinfo = item
        pay_total = 0
        for each in carts:
            pay_total = pay_total + each.total_money
            all_amount = all_amount +each.amount
            if each.amount >Products.objects(_id=each.product_id)[0].amount[each.amount_index]:
               order_allow = 0
            # print(each._id)
        if exinfo == None:
            return render(request, 'error.html')
        else:
            login_mes = "欢迎:" + request.session['user_id']
            return render(request, 'cart.html', {'login_msg': login_mes,
                                             'carts': carts,
                                             'exinfo': exinfo,
                                             'all_amount': all_amount,
                                             "order_allow": order_allow,
                                             'pay_total': pay_total})
    else:
        return render(request, 'login.html', {'login_msg': '登录 / 注册'})


def error(request):
    return render(request, 'error.html')


def about(request):
    if 'user_id' in request.session.keys():
        login_mes = "欢迎:" + request.session['user_id']
        return render(request, 'about.html', {'login_msg': login_mes})
    else:
        return render(request, 'about.html', {'login_msg': '登录 / 注册'})


def login_fun(request):
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


def log_out(request):
    if 'user_id' in request.session.keys():
        del request.session['user_id']
        return HttpResponse("注销成功")
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
        elif len(new1) > 20 or len(new1) < 6:
            return HttpResponse('请输入的密码长度在6至20位')
        else:
            customer.password = str(request.POST.get('new1_passwd'))
            customer.save()
    return HttpResponse('密码修改成功')


def change_password(request):
    if 'user_id' in request.session.keys():
        login_mes = "欢迎:" + request.session['user_id']
        return render(request, 'changed_password.html', {'login_msg': login_mes})
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
        elif len(user_id) > 20 or len(user_id) < 6:
            return HttpResponse('请输入一个账号长度在6至20位')
        elif len(user_passwd) > 20 or len(user_passwd) < 6:
            return HttpResponse('请输入的密码长度在6至20位')
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
    global i
    if 'user_id' not in request.session.keys():
        return HttpResponse('请先登录再进行购买')
    if request.method == "POST":
        value = request.POST.get("id")
        pro = Products.objects(_id=value)[0]
        amount_index = request.POST.get("amount_index")
        amount_index_delete = int(amount_index)-1  # 要删去一定数量的的amount下标
        amount_ca = int(request.POST.get("amount"))
        # if (pro.amount[amount_index_delete] - amount_ca) < 0:
        #     return HttpResponse("该款式库存量不足，添加失败")
        pro.amount[amount_index_delete] = pro.amount[amount_index_delete] - 0
        total_money = amount_ca * pro.sale_price
        # print("*"*10, total_money )
        flag = 1
        user = Customer.objects(_id=request.session['user_id'])[0]
        i = 0
        for cart_item in user.carts:
            i = i + 1
            if cart_item.color == request.POST.get("color") and cart_item.size == request.POST.get("value") and cart_item.product_id == request.POST.get("id"):
                flag = 0
                if cart_item.amount + amount_ca > 99:
                    cart_item.amount = cart_item.amount + 0
                    return HttpResponse("购物车内同规格商品最多99件，请查看购物车或者去买新规格的商品吧")
                else:
                    cart_item.amount = cart_item.amount + amount_ca
                    cart_item.total_money = cart_item.total_money + total_money
                cart_item.save()
        if flag == 1:
            if i >= 10:
                return render(request, "carts_off.html")
            else:
               single = str(int(time.time() * 1000000)) + request.session['user_id']
               cart = Carts(single=single,
                     product_id=pro.id,
                     product_name=pro.name,
                     amount_index=amount_index_delete,
                     size=request.POST.get("value"),
                     price=pro.sale_price,
                     color=request.POST.get("color"),
                     amount=amount_ca,
                     total_money=total_money,
                     src=pro.src)
            try:
                customer = Customer.objects(_id=request.session['user_id'])[0]
                customer.carts.append(cart)
                pro.save()
                customer.save()
            except:
                return HttpResponse('购买异常')
    return HttpResponse('添加购物车成功')


def carts_off(request):
    return render(request, "carts_off.html")


def select_rank(request):
    select_ranks = request.POST.get("rank")
    insert_rank = int(select_ranks)
    customer = Customer.objects(_id=request.session['user_id'])[0]
    customer.select_rank = insert_rank
    customer.save()
    return HttpResponse(203)


def search_result(request, key):
    search_res = False
    login_mes = "登录 / 注册"
    product_res_first = Products.objects(name__contains=key)
    product_res_second = Products.objects(description__contains=key)
    # if product_res_first.first() is not None and product_res_second.first() is not None:
    #     search_res = True
    if product_res_first.first() is not None or product_res_second.first() is not None:
        search_res = True
    if 'user_id' in request.session.keys():
        login_mes = "欢迎:" + request.session['user_id']
    return render(request, 'search_result.html',
                  {'login_msg': login_mes,
                   'product_res_first': product_res_first,
                   'product_res_second': product_res_second,
                   'search_res': search_res})


def order_table(request):
    customer = Customer.objects(_id=request.session['user_id'])[0]
    return render(request, "order_table.html")


def checkout(request):
    global login_msg
    if 'user_id' in request.session.keys():
        customer_ = Customer.objects(_id=request.session['user_id'])
        customer = customer_[0]
        carts = customer.carts
        sr = customer.select_rank
        for item in customer.extra_info:
            if sr == item.rank:
                addresss = item.address
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
                       single_price=p_price, amount=p_amount, total_money=summ, address=addresss)

        # for each in carts:
        #    customer_.update_one(pull__carts=each)
        customer_.update(unset__carts=1)
        order_.save()
        order_id = order_.id
        ordertime = order_.deal_time
        customer.order.append(order_)
        customer.save()
        return render(request, 'checkout.html', {'orderid': order_id,
                                                 'summ': summ,
                                                 'ordertime': ordertime})
    return render(request, 'login.html', {'login_msg': "登录 / 注册"})


def address_fun(request):
    global mess
    new_address = {}
    has_address = False
    if 'user_id' in request.session.keys():
        if len(request.POST.get('name_address')) > 15 or len(request.POST.get('name_address')) < 2:
            mess = "请输入一个长度在2至15位且有效的名字"
            return HttpResponse(json.dumps({'mess': mess, 'new_address': new_address, 'has_address': has_address}))
        elif len(request.POST.get('name_address')) == 0:
            mess = "请不要输入一个空姓名"
            return HttpResponse(json.dumps({'mess': mess, 'new_address': new_address, 'has_address': has_address}))
        elif len(request.POST.get('address_address')) > 30 or len(request.POST.get('address_address')) < 6:
            mess = "请输入一个长度在6至30位且有效的地址"
            return HttpResponse(json.dumps({'mess': mess, 'new_address': new_address, 'has_address': has_address}))
        elif len(request.POST.get('address_address')) == 0:
            mess = "请不要输入空地址"
            return HttpResponse(json.dumps({'mess': mess, 'new_address': new_address, 'has_address': has_address}))
        elif len(request.POST.get('phone_address')) != 11:
            mess = "请输入一个长度为11位且有效的电话号码"
            return HttpResponse(json.dumps({'mess': mess, 'new_address': new_address, 'has_address': has_address}))
        else:
            for each in request.POST.get('phone_address'):
                if ord(each) <= 47 or ord(each) >= 58:
                    mess = "请输入一个长度为11位且有效的电话号码"
                    return HttpResponse(json.dumps({'mess': mess, 'new_address': new_address, 'has_address': has_address}))
            customer = Customer.objects(_id=request.session['user_id']).first()
            for item in customer.extra_info:
                if item.name == request.POST.get('name_address') and item.address == request.POST.get('address_address') and item.phone == request.POST.get("phone_address"):
                    mess = "已存在该收货地址请不要重复添加"
                    return HttpResponse(
                        json.dumps({'mess': mess, 'new_address': new_address, 'has_address': has_address}))

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
    else:
        mess = "请先登录"
        return HttpResponse(json.dumps({'mess': mess, 'new_address': new_address, 'has_address': has_address}))


def delete_product_cart(request):
    pay_total = 0
    single = ""
    if request.method == 'GET' and 'user_id' in request.session.keys():
        single = request.GET.get("id")  # 得到购物车中该商品id
        carts = Customer.objects(_id=request.session['user_id']).first().carts
        for item in carts:
            if item.single == single:
                pro = Products.objects(_id=item.product_id)[0]
                pro.amount[item.amount_index] = pro.amount[item.amount_index] + 0
                pro.save()
                break
        Customer.objects(_id=request.session['user_id']).update_one(pull__carts__single=single)
        customer = Customer.objects(_id=request.session['user_id']).first()
        carts = customer.carts
        for each in carts:
            pay_total = pay_total + each.total_money
        # carts = serializers.serialize("json",Customer.objects(_id=request.session['user_id'])[0].carts)
        return HttpResponse(json.dumps({"id": single, "price": pay_total, "fail": "False"}))
    else:
        return HttpResponse(json.dumps({"id": single, "price": pay_total, "fail": "True"}))


def delete_address(request):
    if request.method == 'GET':
        rank = int(request.GET.get("rank"))  # 得到购物车中该商品id
        # print("-------------IDID",id)
        Customer.objects(_id=request.session['user_id']).update_one(pull__extra_info__rank=rank)
        print(rank)
    return HttpResponse(json.dumps({"rank": rank}))


def show_detail_order(request):
    if request.method == 'GET'and 'user_id' in request.session.keys():
        id = request.GET.get("id")
        obj = ObjectId(id)
        print(type(obj), obj)
        order = Order.objects(_id=obj)[0]
        # print(order,type(order))
        # print(order.total_money, order.deal_time)
        order_info = []
        for each in zip(order.color, order.size, order.amount, order.single_price, order.product_name):
            mapresult = {}
            mapresult['color'] = each[0]
            mapresult['size'] = each[1]
            mapresult['amount'] = each[2]
            mapresult['price'] = each[3]
            mapresult['name'] = each[4]
            order_info.append(mapresult)
        return HttpResponse(json.dumps({"order_info": order_info}))
    return HttpResponse(json.dumps({"order_info": "请先登录"}))
login_msg = "登录 / 注册"

# choice1 = Choice(choice_text="just test django", votes="1")
# poll1 = Poll(question="")
# Create your views here.
