<div id="menu_jsp">
    <form id="order" action="/omo/orders/submitOrder" method="POST" enctype="application/x-www-form-urlencoded">
        <div style="height:30px;margin-top: 10px;margin-left:100px;">
            <button type="submit" class="btn btn-inverse">Order</button>
        </div>
        <div>
            Notes about your order (e.g. "Hold the mayo"): <input type="text" name="notes"/>
        </div>
        ${menuHTML}
        <div style="height:30px;margin-top: 10px;margin-left:100px;">
            <button type="submit" class="btn">Order</button>
        </div>
    </form>
</div>