# -*- coding: utf-8 -*-
# @Time    : 2018/1/8 22:15
# @Author  : k_holmes
# @Email   : 31501324@stu.zucc.edu.cn
# @File    : TestRegister.py
# @Software: PyCharm
import unittest
from django.http import HttpResponse
from se_work.models import Customer
import os,django
os.environ.setdefault("DJANGO_SETTINGS_MODULE", "se2017_django.settings")# project_name 项目名称
django.setup()
class Register(unittest.TestCase):
    def setUp(self):
        pass
    def register(self, user_id, passwd, passwd_confirm):
        # products = Products.objects()
        user_id = user_id
        user_passwd = passwd
        user_confirm_passwd = passwd_confirm
        if len(user_id) == 0:
            return '账号不能为空'
        elif len(user_passwd) == 0 or len(user_confirm_passwd) == 0:
            return '密码不能为空'
        elif user_passwd != user_confirm_passwd:
            return "两次密码不一致"
        elif len(user_id) > 20 or len(user_id) < 6:
            return '请输入一个账号长度在6至20位'
        elif len(user_passwd) > 20 or len(user_passwd) < 6:
            return '请输入一个密码长度在6至20位'
        elif len(Customer.objects(_id=user_id)) != 0:
            return HttpResponse('账户已存在')
        else:
            user_registed = Customer(_id=user_id, password=user_passwd, sex="男")
            user_registed.save()
            # login_msg = '欢迎:' + request.session['user_id']
            return "注册成功"
    def tearDown(self):
         return "测试后成功"

    def test0(self):
        self.assertEqual(self.register("", "11111111", "1111111111"), "账号不能为空")
    def test1(self):
        self.assertEqual(self.register("123456", "11111111", "13123132131241242132asdsadsasad3"), "两次密码不一致")
        self.assertEqual(self.register("123456", "11111112", "1111111111"), "两次密码不一致")
    def test2(self):
         self.assertEqual(self.register("123456", "", "1111111111"), "密码不能为空")
         self.assertEqual(self.register("123456", "1111111111", ""), "密码不能为空")
    def test3(self):
        self.assertEqual(self.register("cdk", "123456", "123456"), "请输入一个账号长度在6至20位")
        self.assertEqual(self.register("cdk2132141241312asdsadsadsadadasdzcdsa", "123456", "123456"), "请输入一个账号长度在6至20位")
    def test4(self):
        self.assertEqual(self.register("cdk123", "12", "12"), "请输入一个密码长度在6至20位")
        self.assertEqual(self.register("cdk123", "1212312421321421321asdsadsad32142141eqe", "1212312421321421321asdsadsad32142141eqe"),
                         "请输入一个密码长度在6至20位")
    def test5(self):
        self.assertEqual(self.register("", "", ""), "账号不能为空")
        self.assertEqual(self.register("", "asd", ""), "账号不能为空")
        self.assertEqual(self.register("", "", "asd"), "账号不能为空")
        self.assertEqual(self.register("", "asde", "asd"), "账号不能为空")
        self.assertEqual(self.register("", "asde", "asdee"), "账号不能为空")
    def test6(self):
        self.assertEqual(self.register("cdkcdk123", "12345612312321312414124213132", "12345612312321312414124213132"), "请输入一个密码长度在6至20位")
        self.assertEqual(self.register("cdkcdk123", "1234", "1234"), "请输入一个密码长度在6至20位")
    def test7(self):
        self.assertEqual(self.register("123", "", ""), "密码不能为空")
        self.assertEqual(self.register("123131sadasdadsadhjkfafsksad", "", ""), "密码不能为空")
    if __name__ == '__main__':
        unittest.main()
