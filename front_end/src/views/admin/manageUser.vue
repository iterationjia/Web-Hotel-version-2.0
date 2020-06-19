<template>
    <div class="manageUser-wrapper">
        <a-tabs>
            <a-tab-pane tab="账户管理" key="1">
                <div style="width: 100%; text-align: right; margin:20px 0">
                    <a-button type="primary" @click="addManager"><a-icon type="plus" />添加酒店管理员</a-button>
                </div>
                <a-table
                        :columns="columns"
                        :dataSource="userList"
                        bordered
                >
                    <span slot="price" slot-scope="text">
                        <span>￥{{ text }}</span>
                    </span>
                    <span slot="action" slot-scope="record">
                      <a-button type="primary" @click="EditUserInfo(record)">修改信息</a-button>
                        <a-divider type="vertical"></a-divider>
                        <a-popconfirm
                                title="确定想删除该用户吗？"
                                @confirm="DeleteUser(record)"
                                okText="确定"
                                cancelText="取消"
                        >
                            <a-button type="danger" >删除用户</a-button>
                        </a-popconfirm>
                    </span>
                </a-table>
            </a-tab-pane>
            <a-tab-pane tab="查看会员" key="2">
                <a-table
                        :columns="columns2"
                        :dataSource="vipList"
                        bordered
                ></a-table>
            </a-tab-pane>
            <a-tab-pane tab="酒店管理" key="3">
                <div style="width: 100%; text-align: right; margin:20px 0">
                    <a-button type="primary" @click="addHotel"><a-icon type="plus" />添加酒店</a-button>
                </div>
                <a-table
                        :columns="columns1"
                        :dataSource="adminHotelList"
                        bordered
                >
                    <span slot="action" slot-scope="record">
                        <a-button type="primary"

                                  @click="SetHotelManager(record)"
                                  v-if="record.managerId>0" >
                            更改管理员
                        </a-button>
                        <a-button
                                type="primary"

                                @click="SetHotelManager(record)"
                                v-if="record.managerId==null" >
                            设置管理员
                        </a-button>
                        <a-divider type="vertical"></a-divider>
                        <a-popconfirm
                                title="确定想删除该酒店吗？"
                                @confirm="DeleteHotel(record)"
                                okText="确定"
                                cancelText="取消"
                        >
                            <a-button type="danger" >删除酒店</a-button>
                        </a-popconfirm>
                    </span>
                </a-table>
            </a-tab-pane>
        </a-tabs>
        <AddManagerModal></AddManagerModal>
        <AddHotelModal></AddHotelModal>
        <SetHotelManagerModal></SetHotelManagerModal>
        <EditUserInfoModal :info="userInfo"></EditUserInfoModal>
    </div>
</template>
<script>
    import { mapGetters, mapMutations, mapActions } from 'vuex'
    import AddManagerModal from './components/addManagerModal'
    import AddHotelModal from '../hotelManager/components/addHotelModal'
    import SetHotelManagerModal from './components/setHotelManagerModal'
    import EditUserInfoModal from './components/editUserInfoModal'
    const columns = [
        {
            title: '用户id',
            dataIndex: 'id',
        },
        {
            title: '用户邮箱',
            dataIndex: 'email',
        },
        {
            title: '用户名',
            dataIndex: 'userName',
        },
        {
            title: '用户密码',
            dataIndex: 'password',
        },
        {
            title: '用户手机号',
            dataIndex: 'phoneNumber',
        },
        {
            title: '信用值',
            dataIndex: 'credit',
        },
        {
            title: '用户身份',
            dataIndex: 'userType',
        },

        {
            title: '操作',
            key: 'action',
            scopedSlots: { customRender: 'action' },
        },
    ];
    const columns1=[
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
            title:'管理员id',
            dataIndex:'managerId',
        },
        {
            title: '操作',
            key: 'action',
            scopedSlots: { customRender: 'action' },
        },



    ];
    const columns2=[
        {
            title: '用户id',
            dataIndex: 'id',
        },
        {
            title: '用户邮箱',
            dataIndex: 'email',
        },
        {
            title: '用户名',
            dataIndex: 'userName',
        },
        {
            title: '用户密码',
            dataIndex: 'password',
        },
        {
            title: '用户手机号',
            dataIndex: 'phoneNumber',
        },
        {
            title: '信用值',
            dataIndex: 'credit',
        },
        {
            title: '会员等级',
            dataIndex: 'lv',
        },
    ];
    export default {
        name: 'manageHotel',
        data(){
            return {
                formLayout: 'horizontal',
                pagination: {},
                columns,
                columns1,
                columns2,
                data: [],
                userInfo:{},
                form: this.$form.createForm(this, { name: 'manageUser' }),
            }
        },
        components: {
            AddManagerModal,
            AddHotelModal,
            SetHotelManagerModal,
            EditUserInfoModal,
        },
        computed: {
            ...mapGetters([
                'addManagerModalVisible',
                'addHotelModalVisible',
                'managerList',
                'userList',
                'vipList',
                'adminHotelList',
                'setHotelManagerModalVisible',
                'editUserInfoModalVisible',
                'hotelid',
                'userid',
                'editUserInfoParams',
            ])
        },
        mounted() {
            this.getManagerList(),
                this.getHotelList(),
                this.getUserList(),
                this.getVipList()
        },
        methods: {

            ...mapActions([
                'getManagerList',
                'getHotelList',
                'deleteUser',
                'deleteHotel',
                'addCommentTable',
                'getUserList',
                'getVipList'
            ]),
            ...mapMutations([
                'set_addHotelModalVisible',
                'set_addManagerModalVisible',
                'set_setHotelManagerModalVisible',
                'set_editUserInfoParams',
                'set_editUserInfoModalVisible',
                'set_HotelId',
                'set_UserId',
            ]),
            addHotel() {
                this.set_addHotelModalVisible(true)
            },
            addManager(){
                this.set_addManagerModalVisible(true)
            },
            EditUserInfo(record){
                this.userInfo=record
                console.log(this.userInfo)
                this.set_UserId(record.id)
                console.log(this.userid)
                this.set_editUserInfoModalVisible(true)
            },
            SetHotelManager(record){
                this.set_HotelId(record.id)
                this.set_setHotelManagerModalVisible(true)
            },
            DeleteUser(record){
                this.deleteUser(record.id)
            },
            DeleteHotel(record){
                this.deleteHotel(record.id)
            }

        }
    }
</script>
<style scoped lang="less">
    .manageUser-wrapper {
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
    .manageUser-wrapper {
        .ant-tabs-bar {
            padding-left: 30px
        }
    }
</style>
<style lang="less">

</style>