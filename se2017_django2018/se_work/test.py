# -*- coding: utf-8 -*-
# @Time    : 2017/12/24 13:30
# @Author  : k_holmes
# @Email   : 31501324@stu.zucc.edu.cn
# @File    : test.py
# @Software: PyCharm

from django.test import TestCase
from .models import *
import sys
import datetime
class CustomerModelTests(TestCase):
    def test_register_time(self):
        customer = Customer(sex="男", password="123456", _id="test1")
        self.assertIs(type(customer.register_time) == datetime.datetime, True)


class ProductsModelTest(TestCase):
    def test_order_status(self):
        order = Order()
        self.assertIs(type(order.deal_time) == datetime.datetime, True)
        self.assertEqual(order.status, "未付款")


