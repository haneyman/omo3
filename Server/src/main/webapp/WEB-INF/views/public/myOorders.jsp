<div class=" masthead">
    <div class="divOrderConfirmation" style="padding:10px;background-color: #7aba7b">
        Your order has been received.  Enjoy!!
    </div>
    <div class="container">
        <h1>Orders</h1>
        <c:forEach items="${map}" var="entry">
            Key = ${entry.key}, value = ${entry.value}<br>
        </c:forEach>
    </div>
</div>