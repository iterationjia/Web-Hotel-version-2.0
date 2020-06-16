const getters = {
  //user
  token: state => state.user.token,
  userId: state => state.user.userId,
  userInfo: state => state.user.userInfo,
  userOrderList: state => state.user.userOrderList,
  userOrderTypeList: state => state.user.userOrderTypeList,
  userHotelOrderList: state => state.hotel.userHotelOrderList,
  userScheduledOrderList: state => {
    return state.user.userOrderList.filter(order => (order.orderState=='已预订'))
  },
  userExecutedOrderList: state => {
    return state.user.userOrderList.filter(order => ((order.orderState=='已执行')||(order.orderState=='已入住')||(order.orderState=='已退房')))
  },
  userErrorOrderList: state => {
    return state.user.userOrderList.filter(order => (((order.orderState!='已执行')&&(order.orderState!='已预订'))&&(order.orderState!='已入住')&&(order.orderState!='已退房')))
  },
  hotelListLoading: state => state.hotel.hotelListLoading,
  hotelList: state => state.hotel.hotelList,
  currentHotelInfo: state => state.hotel.currentHotelInfo,
  currentHotelId: state => state.hotel.currentHotelId,
  orderModalVisible: state => state.hotel.orderModalVisible,
  searchModalVisible: state => state.hotel.searchModalVisible,
  currentOrderRoom: state => state.hotel.currentOrderRoom,
  orderMatchCouponList: state => state.hotel.orderMatchCouponList,
  hotelComments: state => state.hotel.hotelComments,
  showCommentVisible:state => state.user.showCommentVisible,
  commentVisible:state => state.user.commentVisible,

  //admin
  managerList: state => state.admin.managerList,
  userList: state=>state.admin.userList,

  vipList:state=>state.admin.vipList,

  addManagerModalVisible: state => state.admin.addManagerModalVisible,
  addManagerParams: state => state.admin.addManagerParams,
  adminHotelList: state => state.admin.adminHotelList,

  hotelid: state=>state.admin.hotelid,
  userid: state=>state.admin.userid,
  editUserInfoParams:state=>state.admin.editUserInfoParams,
  editUserInfoModalVisible:state=>state.admin.editUserInfoModalVisible,

  setHotelManagerModalVisible:state=>state.admin.setHotelManagerModalVisible,
  //hotelManager
  // orderList: state => state.hotelManager.orderList,
  addHotelModalVisible: state => state.hotelManager.addHotelModalVisible,
  addRoomModalVisible: state => state.hotelManager.addRoomModalVisible,
  manageRoomModalVisible: state => state.hotelManager.manageRoomModalVisible,
  editHotelModalVisible: state => state.hotelManager.editHotelModalVisible,
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
    return state.hotelManager.managerOrderList.filter(order => ((order.orderState=='已执行')||(order.orderState=='已入住')||(order.orderState=='已退房')))
  },
  managerErrorOrderList: state => {
    return state.hotelManager.managerOrderList.filter(order => ((order.orderState!='已执行')&&(order.orderState!='已预订')&&(order.orderState!='已入住')&&(order.orderState!='已退房')))
  },
  hotelDetail: state => state.hotelManager.hotelDetail,
  couponList: state => state.hotelManager.couponList,
  orderDetailVisible: state => state.hotelManager.orderDetailVisible,
  //marketManager
  managerExceptionalOrderList: state => {
    return state.marketManager.orderList.filter(order => ((order.orderState!='已执行')&&(order.orderState!='已预订')&&(order.orderState!='已撤销')&&(order.orderState!='已入住')&&(order.orderState!='已退房')))
  },
  userCredit: state => state.marketManager.userCredit,
  userlv: state => state.marketManager.userlv,
  totalmoney: state => state.marketManager.totalmoney,
}
  
  export default getters