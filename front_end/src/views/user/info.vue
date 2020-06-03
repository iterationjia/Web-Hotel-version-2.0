<template>
    <div class="info-wrapper">
        <a-tabs>
            <a-tab-pane tab="我的信息" key="1">
                <a-form :form="form" style="margin-top: 30px">
                    
                    <a-form-item label="用户名" :label-col="{ span: 3 }" :wrapper-col="{ span: 8, offset: 1  }">
                        <a-input
                            placeholder="请填写用户名"
                            v-decorator="['userName', { rules: [{ required: true, message: '请输入用户名' }] }]"
                            v-if="modify"
                        />
                        <span v-else>{{ userInfo.userName }}</span>
                    </a-form-item>
                    <a-form-item label="邮箱" :label-col="{ span: 3 }" :wrapper-col="{ span: 8, offset: 1 }">
                        <span>{{ userInfo.email }}</span>
                    </a-form-item>
                    
                    <a-form-item label="手机号" :label-col="{ span: 3 }" :wrapper-col="{ span: 8, offset: 1 }">
                        <a-input
                            placeholder="请填写手机号"
                            v-decorator="['phoneNumber', { rules: [{ required: true, message: '请输入手机号' }] }]"
                            v-if="modify"
                        />
                        <span v-else>{{ userInfo.phoneNumber}}</span>
                    </a-form-item>
                    <a-form-item label="信用值" :label-col="{ span: 3 }" :wrapper-col="{ span: 8, offset: 1 }">
                        <span>{{ userInfo.credit }}</span>
                    </a-form-item>
                    <a-form-item label="密码" :label-col="{ span: 3 }" :wrapper-col="{ span: 8, offset: 1 }" v-if="modify">
                        <a-input
                            placeholder="请输入新密码"
                            v-decorator="['password', { rules: [{ required: true, message: '请输入新密码' }] }]"
                        />
                    </a-form-item>
                    <a-form-item :wrapper-col="{ span: 12, offset: 5 }" v-if="modify">
                        <a-button type="primary" @click="saveModify">
                            保存
                        </a-button>
                        <a-button type="default" style="margin-left: 30px" @click="cancelModify">
                            取消
                        </a-button>
                    </a-form-item>
                     <a-form-item :wrapper-col="{ span: 8, offset: 4 }" v-else>
                        <a-button type="primary" @click="modifyInfo">
                            修改信息
                        </a-button>
                    </a-form-item>
                </a-form>
            </a-tab-pane>
            <a-tab-pane tab="我的订单" key="2">
                <div style="margin:20px 0">
                    <a-radio-group default-value="scheduled" button-style="solid" @change="changeUserOrderListType">
                        <a-radio-button value="scheduled">已预订</a-radio-button>
                        <a-radio-button value="executed">已执行</a-radio-button>
                        <a-radio-button value="error">已撤销/异常</a-radio-button>
                    </a-radio-group>
                </div>
                <a-table
                    :columns="columns"
                    :dataSource="userOrderTypeList"
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
                    <a-tag slot="orderState" color="blue" slot-scope="text">
                        {{ text }}
                    </a-tag>
                    <span slot="action" slot-scope="record">
                        <a-button type="primary" size="small" @click="showOrderDetail">订单详情</a-button>
                        <a-divider type="vertical" v-if="record.orderState == '已预订'"></a-divider>
                        <a-popconfirm
                            title="你确定撤销该笔订单吗？"
                            @confirm="confirmCancelOrder(record.id)"
                            @cancel="cancelCancelOrder"
                            okText="确定"
                            cancelText="取消"
                            v-if="record.orderState == '已预订'"
                        >
                            <a-button type="danger" size="small">撤销</a-button>
                        </a-popconfirm>

                        <!--评价-->
                        <a-divider type="vertical" v-else-if="record.orderState == '已执行'"></a-divider>
                        <span v-if="record.orderState == '已执行'">
                            <template v-if="record.star==null">
                                <a-button type="default" size="small" @click="showModal(record.id)">评价</a-button>
                            </template>
                            <template v-if="record.star>0">
                                <a-button type="default" size="small" @click="showAnotherModal(record.id)">已评价</a-button>
                            </template>

                            <a-modal
                                    title="评价"
                                    :visible="visible"
                                    @ok="handleOk"
                                    @cancel="handleCancel"
                                    okText="确定"
                                    cancelText="取消"
                            >
                                <div>
                                    <p>评分</p>
                                    <a-rate v-model="stars" :tooltips="desc" />
                                    <span class="ant-rate-text">{{ desc[value - 1] }}</span>
                                    <br/>
                                    <br/>
                                    <p>评论</p>
                                    <a-textarea placeholder="请输入您的评价" auto-size v-model="comments"/>
                                </div>
                            </a-modal>

                            <a-modal
                                    title="已评价"
                                    :visible="anotherVisible"
                                    @ok="handleAnotherOk"
                                    @cancel="handleAnotherCancel"
                                    okText="确定"
                                    cancelText="取消"
                            >
                                <div>
                                    <p>您的评分</p>
                                    <a-rate v-model="record.star" :tooltips="desc" disabled/>
                                    <span class="ant-rate-text">{{ desc[value - 1] }}</span>
                                    <br/>
                                    <br/>
                                    <p>您的评论</p>
                                    <a-textarea placeholder="请输入您的评价" auto-size v-model="record.comment" disabled/>
                                </div>
                            </a-modal>
                        </span>
                    </span>
                </a-table>
            </a-tab-pane>
        </a-tabs>
        <OrderDetail></OrderDetail>
    </div>
</template>


<script>
import { mapGetters, mapMutations, mapActions } from 'vuex'
import OrderDetail from './components/userOrderDetail'
const columns = [
    {  
        title: '订单号',
        dataIndex: 'id',
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
        title: '状态',
        filters: [{ text: '已预订', value: '已预订' }, { text: '已撤销', value: '已撤销' }, { text: '已入住', value: '已入住' }],
        onFilter: (value, record) => record.orderState.includes(value),
        dataIndex: 'orderState',
        scopedSlots: { customRender: 'orderState' }
    },
    {
      title: '操作',
      key: 'action',
      scopedSlots: { customRender: 'action' },
    },
    
  ];
export default {
    name: 'info',
    data(){
        return {
            modify: false,
            formLayout: 'horizontal',
            pagination: {},
            columns,
            data: [],
            form: this.$form.createForm(this, { name: 'coordinated' }),
            stars: 3,
            desc: ['terrible', 'bad', 'normal', 'good', 'wonderful'],
            visible: false,
            anotherVisible: false,
            comments: null,
            recordId: 0,
            value: null,
        }
    },

    components: {
        OrderDetail
    },
    computed: {
        ...mapGetters([
            'userId',
            'userInfo',
            'userOrderList',
            'userOrderTypeList',
            'userScheduledOrderList',
            'userExecutedOrderList',
            'userErrorOrderList',
        ])
    },
    async mounted() {
        await this.getUserInfo()
        await this.getUserOrders()
    },
    methods: {
        ...mapMutations(['' +
            'set_userOrderListType',
            'set_orderDetailVisible',
        ]),
        ...mapActions([
            'getUserInfo',
            'getUserOrders',
            'updateUserInfo',
            'cancelOrder',
            'updateUserOrderComment',
        ]),
        saveModify() {
            this.form.validateFields((err, values) => {
                if (!err) {
                    const data = {
                        userName: this.form.getFieldValue('userName'),
                        phoneNumber: this.form.getFieldValue('phoneNumber'),
                        password: this.form.getFieldValue('password')
                    }
                    this.updateUserInfo(data).then(()=>{
                        this.modify = false
                    })
                }
            });
        },
        modifyInfo() {
            setTimeout(() => {
                this.form.setFieldsValue({
                    'userName': this.userInfo.userName,
                    'phoneNumber': this.userInfo.phoneNumber,
                })
            }, 0)
            this.modify = true
        },
        cancelModify() {
            this.modify = false
        },
        confirmCancelOrder(orderId){
            this.cancelOrder(orderId)
        },
        cancelCancelOrder() {

        },
        changeUserOrderListType(param){
            this.set_userOrderListType(param.target.value)
        },
        showOrderDetail(){
            this.set_orderDetailVisible(true)
        },
        showModal(num) {
            this.visible = true;
            this.recordId = num;
        },
        showAnotherModal(num){
            this.anotherVisible = true;
            this.recordId = num;
        },
        handleOk() {
            const data = {
                star: this.stars,
                comment: this.comments,
                id: this.recordId,
            }
            this.updateUserOrderComment(data)

            this.visible = false;
        },
        handleCancel(e) {
            console.log('Clicked cancel button');
            this.visible = false;
        },
        handleAnotherOk(){
            this.anotherVisible = false;
        },
        handleAnotherCancel(e) {
            this.anotherVisible = false;
        },
    }
}
</script>
<style scoped lang="less">
    .info-wrapper {
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
    .info-wrapper {
        .ant-tabs-bar {
            padding-left: 30px
        }
    }
</style>
<style lang="less">
    
</style>