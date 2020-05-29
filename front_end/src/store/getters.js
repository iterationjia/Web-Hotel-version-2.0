const getters = {
  //user
  token: state => state.user.token,
  userId: state => state.user.userId,
  userInfo: state => state.user.userInfo,
  userOrderList: state => state.user.userOrderList,
  userOrderTypeList: state => state.user.userOrderTypeList,
  userScheduledOrderList: state => {
    return state.user.userOrderList.filter(order => (order.orderState=='已预订'))
  },
  userExecutedOrderList: state => {
    return state.user.userOrderList.filter(order => (order.orderState=='已执行'))
  },
  userErrorOrderList: state => {
    return state.user.userOrderList.filter(order => ((order.orderState!='已执行')&&(order.orderState!='已预订')))
  },
  hotelListLoading: state => state.hotel.hotelListLoading,
  hotelList: state => state.hotel.hotelList,
  currentHotelInfo: state => state.hotel.currentHotelInfo,
  currentHotelId: state => state.hotel.currentHotelId,
  orderModalVisible: state => state.hotel.orderModalVisible,
  currentOrderRoom: state => state.hotel.currentOrderRoom,
  orderMatchCouponList: state => state.hotel.orderMatchCouponList,
  //admin
  managerList: state => state.admin.managerList,
  addManagerModalVisible: state => state.admin.addManagerModalVisible,
  addManagerParams: state => state.admin.addManagerParams,
  //hotelManager
  // orderList: state => state.hotelManager.orderList,
  addHotelModalVisible: state => state.hotelManager.addHotelModalVisible,
  addRoomModalVisible: state => state.hotelManager.addRoomModalVisible,

  execOrderVisible:state=>state.hotelManager.execOrderVisible,

  couponVisible: state => state.hotelManager.couponVisible,
  addCouponVisible: state => state.hotelManager.addCouponVisible,
  activeHotelId: state => state.hotelManager.activeHotelId,
  managerHotelList: state => state.hotelManager.managerHotelList,
  managerOrderList: state => state.hotelManager.managerOrderList,
  managerOrderTypeList: state => state.hotelManager.managerOrderTypeList,
  managerScheduledOrderList: state => {
     return state.hotelManager.managerOrderList.filter(order => (order.orderState=='已预订'))
  },
  managerExecutedOrderList: state => {
    return state.hotelManager.managerOrderList.filter(order => (order.orderState=='已执行'))
  },
  managerErrorOrderList: state => {
    return state.hotelManager.managerOrderList.filter(order => ((order.orderState!='已执行')&&(order.orderState!='已预订')))
  },
  couponList: state => state.hotelManager.couponList,
  orderDetailVisible: state => state.hotelManager.orderDetailVisible,
  //marketManager
  managerExceptionalOrderList: state => {
    return state.marketManager.orderList.filter(order => ((order.orderState!='已执行')&&(order.orderState!='已预订')&&(order.orderState!='已撤销')))
  }
}
  
  export default getters