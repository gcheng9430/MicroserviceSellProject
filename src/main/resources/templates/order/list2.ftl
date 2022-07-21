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


<#--弹窗-->
<div class="modal fade" id="myModal" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h4 class="modal-title" id="myModalLabel">
                    提醒 Reminder
                </h4>
            </div>
            <div class="modal-body">
                您有新的订单 You have a new order
            </div>
            <div class="modal-footer">
                <button onclick="javascript:document.getElementById('notice').pause()" type="button" class="btn btn-default" data-dismiss="modal">关闭 Close</button>
                <button onclick="location.reload()" type="button" class="btn btn-primary">查看新的订单 To new order </button>
            </div>
        </div>

    </div>

</div>
<#--播放音乐-->
<audio id="notice" loop="loop">
    <source src="/sell/mp3/song.mp3" type = "audio/mpeg" />
</audio>



<script src="https://cdn.bootcss.com/jquery/1.12.4/jquery.min.js"></script>
<script src="https://cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<script>
    var websocket = null;
    // 先查浏览器能不能用
    if('WebSocket' in window){
        websocket = new WebSocket('ws://sell.natapp4.cc/sell/webSocket');

    }else{
        alert('该浏览器不支持websocket');
    }
    websocket.onopen = function(event){
        console.log('建立连接');
    }
    websocket.onclose = function(event){
        console.log('连接关闭');
    }
    websocket.onmessage = function(event){
        console.log('收到消息'+event.data);
        //弹窗提醒，播放音乐
        //弹窗触发需要JQuery
        $(`#myModal`).ariaModal('show');
        //用html5原生的播放音乐
        document.getElementById('notice').play();





    }
    websocket.onerror = function(){
        alert('websocket通信发生错误！');
    }
    //窗口关闭的时候要把websocket关闭掉
    window.onbeforeunload = function(){
        websocket.close();
    }


</script>
</body>
</html>




