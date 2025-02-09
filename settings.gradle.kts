rootProject.name = "MarketPlaceMicroservices"
include("orders_mp:mp_orders")
include("notifications_mp:mp_notifications")
include("gateway-api:api-gateway")
include("warehouse_mp:mp_warehouse")
include("eureka_server:eureka-server")
include("eureka_client:eureka-client")
