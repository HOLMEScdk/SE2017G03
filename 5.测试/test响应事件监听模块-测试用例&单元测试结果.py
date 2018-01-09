import unittest
import se_work.models

class SzTestCase(unittest.TestCase):

    def ajax_add_cart(self, user_id, amount, value, color):
        global pro
        if user_id == "登录 / 注册":
           return "请先登录"
        else:
            pro = se_work.models.Products.objects(_id="aa1")[0]
            amount_index_delete = 0
            try:
                pro.amount[0] = pro.amount[0] - 1
                pro.save()
            except:
                return "库存量不足，添加失败"
            try:
                if(value == None):
                    return "请选择规格"
                for size_item in pro.size:
                    if value == size_item:
                              Size = value
                for color_item in pro.color:
                     if color == color_item:
                          Color = color
            except:
                return "请选择规格"
            try:
              total_money = amount * pro.sale_price
              cart = se_work.models.Carts(single="test",
                                        product_id=pro.id,
                                        product_name=pro.name,
                                        amount_index=amount_index_delete,
                                        size=Size,
                                        price=pro.sale_price,
                                        color=Color,
                                        amount=amount,
                                        total_money=total_money,
                                        src=pro.src)
              customer = se_work.models.Customer.objects(_id=user_id)[0]
              customer.carts.append(cart)
              customer.save()
            except:
                return "操作异常"
            return "添加购物车成功"

    def test000(self):
        self.assertEqual(self.ajax_add_cart("登录 / 注册", None, None, None), "请先登录")

    def test001(self):
        for i in range(10):
          self.assertEqual(self.ajax_add_cart("登录 / 注册", i, None, None), "请先登录")


    def test011(self):
        self.assertEqual(self.ajax_add_cart("登录 / 注册", None, "xxl", "red"), "请先登录")
        self.assertEqual(self.ajax_add_cart("登录 / 注册", None, "xl", "RED"), "请先登录")

    def test010(self):
        self.assertEqual(self.ajax_add_cart("s104881472", None, None, None), "请选择规格")

    def test100(self):
        for i in range(10):
          self.assertEqual(self.ajax_add_cart("s104881472", i, None, None), "请选择规格")

    def test101(self):
        self.assertEqual(self.ajax_add_cart("s104881472", None, "xxl", "red"), "操作异常")
        self.assertEqual(self.ajax_add_cart("s104881472", None, "xl", "RED"), "操作异常")

    def test111(self):
        for i in range(10):
          self.assertEqual(self.ajax_add_cart("s104881472", i, "red", "xxl"), "添加购物车成功")
          self.assertEqual(self.ajax_add_cart("s104881472", i, "RED", "xl"), "添加购物车成功")

    def test110(self):
        for i in range(10):
          self.assertEqual(self.ajax_add_cart("s104881472", i, None, None), "请选择规格")


if __name__ == "__main__":
    unittest.main()  
