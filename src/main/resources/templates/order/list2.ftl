<html>
<#include "../common/header.ftl">
<body>
<div id=wrapper" class="toggled">
<#--    边栏side bar-->
    <#include "../common/nav.ftl">
<#--    主要内容区域-->
    <div id="page-content-wrapper">
        <div class="container-fluid">
            <div class="row clearfix">
                <div class="col-md-12 column">
                    <table class="table table-bordered table-condensed">
                        <thead>
                        <tr>
                            <th>Seller Id</th>
                            <th>Name</th>
                            <th>Phone Number</th>
                            <th>Address</th>
                            <th>Amount</th>
                            <th>Order Status</th>
                            <th>Creation time</th>
                            <th colspan="2">More Options</th>
                        </tr>
                        </thead>
                        <tbody>
                        <#list orderDTOPage.content as orderDTO>
                            <tr>
                                <td>${orderDTO.orderId}</td>
                                <td>${orderDTO.buyerName}</td>
                                <td>${orderDTO.buyerPhone}</td>
                                <td>${orderDTO.buyerAddress}</td>
                                <td>${orderDTO.orderAmount}</td>
                                <td>${orderDTO.orderStatus}</td>
                                <td>${orderDTO.payStatus}</td>
                                <td>${orderDTO.createTime}</td>
                                <td >
                                    <a href="/sell/seller/order/detail?orderId=${orderDTO.orderId}">Detail</a>

                                </td>
                                <td>
                                    <#--                        如果订单状态是已取消就不再显示取消了 必须是不等于取消才显示-->
                                    <#if orderDTO.getOrderStatusEnum().message == "新订单">
                                        <a href="/sell/seller/order/cancel?orderId=${orderDTO.orderId}">Cancel</a>
                                    </#if>
                                </td>
                            </tr>
                        </#list>
                        </tbody>
                    </table>
                </div>
                <#--        Pagination-->
                <div class="col-md-12 column">
                    <ul class="pagination pull-right">
                        <#--                小于等于1的话不能点上一页-->
                        <#if currentPage lte 1>
                            <li class="disabled"><a href="#">Prev</a></li>
                        <#else>
                            <li><a href="/sell/seller/order/list?page=${currentPage-1}&size=${size}">Prev</a></li>
                        </#if>
                        <#--                循环做中间的页数-->
                        <#list 1..orderDTOPage.getTotalPages() as index>
                        <#--                    就是1到总页数-->
                            <#if currentPage == index>
                            <#--                        如果当前页 要灰掉不能点-->
                                <li class="disabled"><a href="#">${index}</a></li>
                            <#else>
                                <li><a href="/sell/seller/order/list?page=${index}&size=${size}">${index}</a></li>
                            </#if>

                        </#list>
                        <#--                如果在最后一页 那下一页也不能点了-->
                        <#if currentPage gte orderDTOPage.getTotalPages()>
                            <li class="disabled"><a href="#">Next</a></li>
                        <#else>
                            <li><a href="/sell/seller/order/list?page=${currentPage+1}&size=${size}">Next</a></li>
                        </#if>
                    </ul>
                </div>
            </div>
        </div>
    </div>

</div>
</body>
</html>




