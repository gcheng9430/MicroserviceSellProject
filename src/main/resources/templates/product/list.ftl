<html>
<#include "../common/header.ftl">

<body>
<div id="wrapper" class="toggled">

    <#--边栏sidebar-->
    <#include "../common/nav.ftl">

    <#--主要内容content-->
    <div id="page-content-wrapper">
        <div class="container-fluid">
            <div class="row clearfix">
                <div class="col-md-12 column">
                    <table class="table table-bordered table-condensed">
                        <thead>
                        <tr>
                            <th>Product Id</th>
                            <th>Name</th>
                            <th>Icon</th>
                            <th>Price</th>
                            <th>Stock</th>
                            <th>Description</th>
                            <th>Category</th>
                            <th>Create Time</th>
                            <th>Update Time</th>
                            <th colspan="2">More Option</th>
                        </tr>
                        </thead>
                        <tbody>

                        <#list productInfoPage.content as productInfo>
                        <tr>
                            <td>${productInfo.productId}</td>
                            <td>${productInfo.productName}</td>
                            <td><img height="100" width="100" src="${productInfo.productIcon}" alt=""></td>
                            <td>${productInfo.productPrice}</td>
                            <td>${productInfo.productStock}</td>
                            <td>${productInfo.productDescription}</td>
                            <td>${productInfo.categoryType}</td>
                            <td>${productInfo.createTime}</td>
                            <td>${productInfo.updateTime}</td>
                            <td><a href="/sell/seller/product/index?productId=${productInfo.productId}">Change</a></td>
                            <td>
                                <#if productInfo.getProductStatusEnum().message == "在架">
                                    <a href="/sell/seller/product/off_sale?productId=${productInfo.productId}">Make it Off Sale</a>
                                <#else>
                                    <a href="/sell/seller/product/on_sale?productId=${productInfo.productId}">Make it On Sale</a>
                                </#if>
                            </td>
                        </tr>
                        </#list>
                        </tbody>
                    </table>
                </div>

            <#--分页-->
                <div class="col-md-12 column">
                    <ul class="pagination pull-right">
                    <#if currentPage lte 1>
                        <li class="disabled"><a href="#">Prev</a></li>
                    <#else>
                        <li><a href="/sell/seller/order/list?page=${currentPage - 1}&size=${size}">Prev</a></li>
                    </#if>

                    <#list 1..productInfoPage.getTotalPages() as index>
                        <#if currentPage == index>
                            <li class="disabled"><a href="#">${index}</a></li>
                        <#else>
                            <li><a href="/sell/seller/order/list?page=${index}&size=${size}">${index}</a></li>
                        </#if>
                    </#list>

                    <#if currentPage gte productInfoPage.getTotalPages()>
                        <li class="disabled"><a href="#">Next</a></li>
                    <#else>
                        <li><a href="/sell/seller/order/list?page=${currentPage + 1}&size=${size}">Next</a></li>
                    </#if>
                    </ul>
                </div>
            </div>
        </div>
    </div>

</div>
</body>
</html>