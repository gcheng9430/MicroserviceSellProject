<html>
<#include "../common/header.ftl">
<body>

<div id=wrapper" class="toggled">
    <#--    边栏side bar-->
    <#include "../common/nav.ftl">
    <#--    主要内容区域-->
    <div id="page-content-wrapper">
        <div class="container">
    <div class="row clearfix">
<#--        订单master表数据-->
        <div class="col-md-4 column">
            <table class="table">
                <thead>
                <tr>
                    <th>Order Id</th>
                    <th>Order Amount</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td>${orderDTO.orderId}</td>
                    <td>${orderDTO.orderAmount}</td>
                </tr>
                </tbody>
            </table>
        </div>
<#--        订单详情表数据-->
        <div class="col-md-12 column">
            <table class="table">
                <thead>
                <tr>
                    <th>Product Id</th>
                    <th>Product Name</th>
                    <th>Product Price</th>
                    <th>Count</th>
                    <th>Total</th>
                </tr>
                </thead>
                <tbody>
<#--                用循环填表-->
                <#list orderDTO.orderDetailList as orderDetail>
                <tr>
                    <td>${orderDetail.productId}</td>
                    <td>${orderDetail.productName}</td>
                    <td>${orderDetail.productPrice}</td>
                    <td>${orderDetail.productQuantity}</td>
                    <td>${orderDetail.productPrice * orderDetail.productQuantity}</td>
                </tr>
                </#list>
                </tbody>
            </table>
        </div>

<#--        操作-->
        <div class="col-md-12 column">
            <#if orderDTO.getOrderStatusEnum().message == "新订单">
            <a href="/sell/seller/order/finish?orderId=${orderDTO.orderId}" type="button" class="btn btn-default btn-primary">Complete Order</a>
            <a href="/sell/seller/order/cancel?orderId=${orderDTO.orderId}" type="button" class="btn btn-default btn-danger">Cancel Order</a>
            </#if>
        </div>
    </div>
</div>
    </div>
</div>
</body>
</html>