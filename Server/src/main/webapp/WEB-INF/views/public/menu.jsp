<div id="menu_jsp">
    <form id="order" action="/omo/orders/publicCreate" method="POST" enctype="application/x-www-form-urlencoded">
        <div style="height:30px;margin-top: 10px;margin-left:100px;">
            <button type="submit" class="btn btn-inverse">Order</button>
        </div>
        ${menuHTML}
        <button type="submit" class="btn">Order</button>
    </form>
</div>