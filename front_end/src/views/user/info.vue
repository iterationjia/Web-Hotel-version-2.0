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
                    <a-form-item label="头像" :label-col="{ span: 3 }" :wrapper-col="{ span: 8, offset: 1 }">

                        <div v-if="modify">
                            <a-upload :file-list="fileList" list-type="picture" :remove="handleRemove" :before-upload="beforeUpload">
                                <a-button v-if="fileList.length < 1"> <a-icon type="upload" /> Select File </a-button>
                            </a-upload>
                            <a-button
                                    type="primary"
                                    :disabled="fileList.length === 0"
                                    :loading="uploading"
                                    style="margin-top: 16px"
                                    @click="handleUpload"
                            >
                                {{ uploading ? '上传中' : '上传' }}
                            </a-button>
                        </div>
                        <span v-else><a-avatar :src="'data:image/jpeg;base64,'+userInfo.avatarurl" size="large"></a-avatar></span>
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
                    <a-form-item label="等级" :label-col="{ span: 3 }" :wrapper-col="{ span: 8, offset: 1 }">
                        <span>{{ userInfo.lv }}</span>
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
                        <a-button type="primary" size="small" @click="showOrderDetail(record)">订单详情</a-button>
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
                        <a-divider type="vertical" v-else-if="record.orderState == '已退房'"></a-divider>
                        <span v-if="record.orderState == '已退房'">
                            <template v-if="record.star==null">
                                <a-button type="default" size="small" @click="commentModal(record.id,record.hotelId)">评价</a-button>
                            </template>
                            <template v-if="record.star>0">
                                <a-button type="default" size="small" @click="showCommentModal(record)">已评价</a-button>
                            </template>
                        </span>
                    </span>
                </a-table>
            </a-tab-pane>
        </a-tabs>
        <OrderDetail :info="orderInfo"></OrderDetail>
        <ShowComment :info="showCommentInfo"></ShowComment>
        <Comments :recordId="recordId" :hotelId="hotelId"></Comments>
    </div>
</template>


<script>
import { mapGetters, mapMutations, mapActions } from 'vuex'
import OrderDetail from './components/userOrderDetail'
import ShowComment from './components/showComment'
import Comments from './components/comment'
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
            hotelId:0,
            formLayout: 'horizontal',
            pagination: {},
            columns,
            data: [],
            form: this.$form.createForm(this, { name: 'coordinated' }),
            recordId: 0,
            value: null,
            orderInfo: {},
            showCommentInfo: {},
            fileList:[],
            uploading:false
        }
    },

    components: {
        OrderDetail,
        ShowComment,
        Comments,
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
            'set_showCommentVisible',
            'set_commentVisible',
        ]),
        ...mapActions([
            'getUserInfo',
            'getUserOrders',
            'updateUserInfo',
            'updateUserAvatar',
            'cancelOrder',
        ]),
        saveModify() {
            this.form.validateFields((err, values) => {
                if (!err) {
                    const data = {
                        userName: this.form.getFieldValue('userName'),
                        phoneNumber: this.form.getFieldValue('phoneNumber'),
                        password: this.form.getFieldValue('password'),
                        avatarurl: this.form.getFieldValue('avatarurl'),
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
                    'avatarurl': this.userInfo.avatarurl,
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
        showOrderDetail(record){
            this.orderInfo = record
            console.log(record)
            this.set_orderDetailVisible(true)
        },
        commentModal(id,hotelId) {
            this.recordId = id;
            this.hotelId = hotelId;
            this.set_commentVisible(true);
        },
        showCommentModal(record){
            this.showCommentInfo = record;
            this.set_showCommentVisible(true);
        },
        handleRemove(file) {
            const index = this.fileList.indexOf(file);
            const newFileList = this.fileList.slice();
            newFileList.splice(index, 1);
            this.fileList = newFileList;
        },
        beforeUpload(file) {
            const isLt1M = file.size / 1024 / 1024 < 1;
            if (!isLt1M) {
                this.$message.error('图片必须小于 1MB!');
                return true;
            } else {
                this.fileList = [...this.fileList, file];
                return false;
            }
        },
        handleUpload(){
            this.updateUserAvatar(this.fileList[0])
            this.fileList = []
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