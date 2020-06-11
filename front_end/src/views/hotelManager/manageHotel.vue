<template>
    <div class="manageHotel-wrapper">
        <a-tabs>
            <a-tab-pane tab="酒店管理" key="1">
                <div style="width: 100%; text-align: right; margin:20px 0">
                    <a-button type="primary" @click="addHotel"><a-icon type="plus" />添加酒店</a-button>
                </div>
                 <a-table
                    :columns="columns1"
                    :dataSource="managerHotelList"
                    bordered
                >
                    <span slot="action" slot-scope="record">
                        <a-button type="primary" size="small" @click="manageRoom(record)">客房管理</a-button>
                        <a-divider type="vertical"></a-divider>
                        <a-button type="info" size="small" @click="editHotel(record)">编辑酒店</a-button>
                        <a-divider type="vertical"></a-divider>
                        <a-button type="info" size="small" @click="showCoupon(record)">优惠策略</a-button>
                        <a-divider type="vertical"></a-divider>
                        <a-popconfirm
                            title="确定想删除该酒店吗？"
                            @confirm="doDeleteHotel(record)"
                            okText="确定"
                            cancelText="取消"
                        >
                            <a-button type="danger" size="small">删除酒店</a-button>
                        </a-popconfirm>
                    </span>
                </a-table>
            </a-tab-pane>
            <a-tab-pane tab="订单管理" key="2">
                <div style="margin:20px 0">

                    <a-radio-group default-value="scheduled" button-style="solid" @change="changeManagerOrderListType">
                        <a-radio-button value="scheduled">已预订</a-radio-button>
                        <a-radio-button value="executed">已执行</a-radio-button>
                        <a-radio-button value="error">已撤销/异常</a-radio-button>
                    </a-radio-group>
                </div>
                <a-table
                    :columns="columns2"
                    :dataSource="managerOrderTypeList"
                    bordered
                >
                    <span slot="price" slot-scope="text">
                        <span>￥{{ text }}</span>
                    </span>
                    <span slot="roomType" slot-scope="text">
                        <span v-if="text == 'BigBed'">大床房</span>
                        <span v-if="text == 'DoubleBed'">双床房</span>
                        <span v-if="text == 'Family'">家庭房</span>
                    </span>
                    <span slot="action" slot-scope="record">
                        <a-button type="primary" size="small" @click="showOrderDetail(record)">订单详情</a-button>
                        <a-divider type="vertical"></a-divider>

                        <a-popconfirm
                                title="确定想执行该订单吗？"
                                @confirm="ExecOrder(record)"
                                okText="确定"
                                cancelText="取消"
                                v-if="record.orderState=='已预订'"
                        >
                            <a-button  type="default" size="small">执行订单</a-button>
                        </a-popconfirm>
                        <a-divider type="vertical" v-if="record.orderState=='已预订'"></a-divider>
                        <a-popconfirm
                                title="确定将该订单置为异常吗？"
                                @confirm="SetOrderExcep(record)"
                                okText="确定"
                                cancelText="取消"
                                v-if="record.orderState=='已预订'"
                        >
                            <a-button  type="default" size="small">订单逾期</a-button>
                        </a-popconfirm>
                        <a-divider type="vertical" v-if="record.orderState=='已预订'"></a-divider>
                                                <a-popconfirm
                                                        title="确定恢复该订单吗？"
                                                        @confirm="RecoverOrder(record)"
                                                        okText="确定"
                                                        cancelText="取消"
                                                        v-if="record.orderState=='异常'"
                                                >
                            <a-button  type="default" size="small">恢复订单</a-button>
                        </a-popconfirm>
                        <a-divider type="vertical" v-if="record.orderState=='异常'"></a-divider>
                        <a-popconfirm
                                title="确定想退房吗？"
                                @confirm="checkOut(record)"
                                okText="确定"
                                cancelText="取消"
                                v-if="record.orderState=='已入住'"
                        >
                            <a-button  type="default" size="small">退房</a-button>
                        </a-popconfirm>
                        <a-divider type="vertical" v-if="record.orderState=='已入住'"></a-divider>

                        <a-popconfirm
                            title="确定想删除该订单吗？"
                            @confirm="deleteOrder(record)"
                            okText="确定"
                            cancelText="取消"
                        >
                            <a-button type="danger" size="small">删除订单</a-button>
                        </a-popconfirm>
                    </span>
                </a-table>
            </a-tab-pane>
            
        </a-tabs>
        <AddHotelModal></AddHotelModal>
        <ManageRoomModal></ManageRoomModal>
        <EditHotelModal :info="hotelInfo"></EditHotelModal>
        <AddRoomModal></AddRoomModal>
        <Coupon></Coupon>
        <OrderDetail :info="orderInfo"></OrderDetail>
    </div>
</template>
<script>
import { mapGetters, mapMutations, mapActions } from 'vuex'
import AddHotelModal from './components/addHotelModal'
import ManageRoomModal from './components/manageRoomModal'
import EditHotelModal from './components/editHotelModal'
import AddRoomModal from './components/addRoomModal'
import Coupon from './components/coupon'
import OrderDetail from './components/orderDetail'
const moment = require('moment')
const columns1 = [
    {  
        title: '酒店名',
        dataIndex: 'name',
    },
    {
        title: '商圈',
        dataIndex: 'bizRegion',
    },
    {
        title: '地址',
        dataIndex: 'address',
    },
    {
        title: '酒店星级',
        dataIndex: 'hotelStar'
    },
    {
        title: '评分',
        dataIndex: 'rate',
    },
    {
        title: '简介',
        dataIndex: 'description',
    },
    {
        title:'总销售额',
        dataIndex:'totalMoney',
    },
    {
      title: '操作',
      key: 'action',
      scopedSlots: { customRender: 'action' },
    },
  ];
const columns2 = [
    {  
        title: '订单号',
        dataIndex: 'id',
    },
    {
        title: '订单状态',
        dataIndex: 'orderState',
    },
    {  
        title: '酒店名',
        dataIndex: 'hotelName',
    },
    {
        title: '房型',
        dataIndex: 'roomType',
        scopedSlots: { customRender: 'roomType' }
    },
    {
        title: '房间数',
        dataIndex: 'roomNum',
    },
    {
        title: '入住时间',
        dataIndex: 'checkInDate',
        scopedSlots: { customRender: 'checkInDate' }
    },
    {
        title: '离店时间',
        dataIndex: 'checkOutDate',
        scopedSlots: { customRender: 'checkOutDate' }
    },
    {
        title: '入住人数',
        dataIndex: 'peopleNum',
    },
    {
        title: '房价',
        dataIndex: 'price',
    },
    {
      title: '操作',
      key: 'action',
      scopedSlots: { customRender: 'action' },
    },
  ];
export default {
    name: 'manageHotel',
    data(){
        return {
            formLayout: 'horizontal',
            pagination: {},
            columns1,
            columns2,

            form: this.$form.createForm(this, { name: 'manageHotel' }),
            hotelInfo: {},
            orderInfo: {}
        }
    },
    components: {
        AddHotelModal,
        ManageRoomModal,
        EditHotelModal,
        AddRoomModal,
        Coupon,
        OrderDetail,
    },
    computed: {
        ...mapGetters([
            // 'orderList',
            'hotelList',
            'managerHotelList',
            'managerOrderTypeList',
            'managerScheduledOrderList',
            'managerExecutedOrderList',
            'managerErrorOrderList',
            'addHotelModalVisible',
            'addRoomModalVisible',
            'activeHotelId',
            'couponVisible',
        ]),
    },
    mounted() {
        // await this.getHotelList()
        this.getManagerHotelList()
        // await this.getAllOrders()
        this.set_managerOrderListType("scheduled")
        this.getManagerOrderList()

    },
    methods: {
        ...mapMutations([
            'set_addHotelModalVisible',
            'set_manageRoomModalVisible',
            'set_editHotelModalVisible',
            'set_couponVisible',
            'set_activeHotelId',
            'set_orderDetailVisible',
            'set_managerOrderListType',
            'set_managerOrderTypeList'
        ]),
        ...mapActions([
            'getHotelList',
            'getHotelDetail',
            'deleteHotelByManager',
            'deleteOrderByManager',
            'getManagerHotelList',
            'getManagerOrderList',
            // 'getAllOrders',
            'execOrder',
            'setOrderExcep',
            'checkOutOrder',
            'getHotelCoupon',
            'changeHotelTotalMoney',
            'recoverOrder',
        ]),
        addHotel() {
            this.set_addHotelModalVisible(true)
        },
        editHotel(record){
            this.set_activeHotelId(record.id)
            this.hotelInfo = record
            this.set_editHotelModalVisible(true)
        },
        manageRoom(record) {
            this.set_activeHotelId(record.id)
            this.getHotelDetail()
            this.set_manageRoomModalVisible(true)
        },
        showCoupon(record) {
            this.set_activeHotelId(record.id)
            this.set_couponVisible(true)
            this.getHotelCoupon()
        },
        doDeleteHotel(record){
            this.deleteHotelByManager(record.id)
        },
        deleteOrder(record){
            // 我的删除订单和郭增嘉的撤销订单有冲突，先不写
            // this.deleteOrderByManager({
            //     id:record.id
            //
            // })
        },
        checkOut(record){
            this.checkOutOrder(record)
        },
        RecoverOrder(record){
            this.recoverOrder(record.id)
        },
        SetOrderExcep(record){
            //console.log(record)
            this.setOrderExcep(record.id)
        },
        ExecOrder(record){

            this.execOrder(record.id)
            this.getManagerHotelList()
           // this.
        },
        changeManagerOrderListType(param){
            this.set_managerOrderListType(param.target.value)
            this.set_managerOrderTypeList()
        },
        showOrderDetail(record){
            this.orderInfo = record
            this.set_orderDetailVisible(true)
        }

    }
}
</script>
<style scoped lang="less">
    .manageHotel-wrapper {
        padding: 50px;
        .chart {
            display: flex;
            align-items: center;
            justify-content: space-between;
            margin-top: 20px
        }
    }
</style>
<style lang="less">
    .manageHotel-wrapper {
        .ant-tabs-bar {
            padding-left: 30px
        }
    }
</style>
<style lang="less">
    
</style>