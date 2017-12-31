from django.db import models
from mongoengine import *
import datetime
import pymongo

#My model
#connect = ('database_name', host = '', username = '', password = '')
connect('SE2017')

# client = pymongo.MongoClient('10.134.80.119


class AddExtraInfo(EmbeddedDocument):
    rank = IntField()
    name = StringField()
    address = StringField()
    phone = StringField(max_length=11)


class Carts(EmbeddedDocument):
    _id = DateTimeField(required=True, default=datetime.datetime.now)
    product_id = StringField()
    product_name = StringField()
    size = StringField()
    price = FloatField()
    color = StringField()
    amount = IntField()
    total_money = FloatField()
    src = StringField()


class Order(DynamicDocument):
    deal_time = DateTimeField(required=True, default=datetime.datetime.now)
    status = StringField(default="未付款")
    product_name = ListField(StringField())  # Because of the goods in cart may be plenty of therefore we use Listfield here
    color = ListField(StringField())
    size = ListField(StringField())
    single_price = ListField(FloatField())
    amount = ListField(IntField())
    total_money = FloatField()  # 总金额 上面的单价是每个商品的单价


class Customer(DynamicDocument):
    _id = StringField(required=True, primary_key=True)
    sex = StringField(required=True)
    password = StringField(required=True, max_length=16)
    extra_info = ListField(EmbeddedDocumentField(AddExtraInfo))
    carts = ListField(EmbeddedDocumentField(Carts))
    order = ListField(ReferenceField(Order, reverse_delete_rule=CASCADE))
    register_time = DateTimeField(default=datetime.datetime.now)
    '''
    cart is referenced by customer,each cart is individual  access via its _id
    '''


class Products(DynamicDocument):
    _id = StringField(required=True, primary_key=True)
    name = StringField(required=True)
    description = StringField()
    class_belong = StringField(required=True, max_length=100)
    size = ListField(StringField(max_length=50))
    color = ListField(StringField(max_length=50))
    raw_price = FloatField(required=True)
    sale_price = FloatField(required=True)
    agent_price = FloatField(default=100)
    time_import = DateTimeField(default=datetime.datetime.now)
    time_end_sale = StringField()
    amount = IntField(default=100)
    src = StringField(required=True)


'''
when a customer touched a good then put this product into cart and extract its information
'''

'''
初步的用户注册 产品等信息
'''
# customer1 = Customer(_id="CDK33", password="1234562")
# customer1.save()
# product = Products(id="second", name="second2", class_belong="clothes", raw_price=23, sale_price=50)

# product1 = Products(_id="aa2", name="second_try2", class_belong="Test", raw_price=150.0, sale_price=250.0, size=["XXL"],
#                     color=["RED"])
# product1.save()
id
'''
追加地址等额外信息
'''
# ans = Customer.objects(_id="CDK")
# if len(ans) > 0:
#     customer1 = ans[0]
#     print(customer1.password, customer1._id)
# add_extra1 = AddExtraInfo(address="ZJU", phone="110", name="CDK")
# customer1.extra_info.append(add_extra1)
# customer1.save()

'''
购物车添加信息
'''
# car1 = Carts(productid="aa2", name="second_try", size="XXL", color="RED", price=250.0, amount=1)
# car1.save()
# Customer.objects(_id="CDK123").update_one(push__carts=car1) #search from Mongo therefore must store before update
'''
订单信息
'''
# order = Order(product_name=["xxx33"], total_money=130)
# order.save()
'''
文档中的列表查询
'''
# order = Order.objects(product_name__0="xxx")
# for each in order:
#     print(each.product_name)
'''
移除列表中的一块
'''
# order = Order.objects(product_name__0="xxx22").first()
# Customer.objects(order=order).update_one(pull__order=order)

# Customer.objects(_id="CDK33").update_one(push__order=order)
# Customer.objects(_id="CDK123").update_one(push__order=order)

'''
删除操作
'''
# order = Order.objects()
# for each in order:
#     each.delete()

'''
特殊查询
'''
# order = Order.objects()
# for each in order:  # customer里面的订单属性拥有某一个订单
#     customer = Customer.objects(order=each._id)  # order虽然是一个数组 但是满足条件即可得到其值
#     for cu in customer:
#         print(cu._id)
'''
特定删除 虽然测试样例都是只有1个但是为了代码的健壮性 都用了for
'''
# order = Order.objects(product_name=["xxx11"])

# Customer.objects(_id="45CDK12345").update_one(push__order=order[0])
# order.delete()
# for each in order:
#     customer = Customer.objects(order=each._id).first()
#     # print(customer._id)
#     customer.update(pull_following=each)

'''
Pymongo operation
'''
# customer = db.customer
# for cu in customer.find({"_id":"45CDK1234"}):
#     pprint.pprint(cu)
# print(customer.count())  # get total instances
