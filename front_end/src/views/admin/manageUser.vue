<template>
    <div class="manageUser-wrapper">
        <a-tabs>
            <a-tab-pane tab="账户管理" key="1">
                <div style="width: 100%; text-align: right; margin:20px 0">
                    <a-button type="primary" @click="doAddCommentTable"><a-icon type="plus" />数据库加表</a-button>
                    <a-button type="primary" @click="addManager"><a-icon type="plus" />添加用户</a-button>
                </div>
                <a-table
                    :columns="columns"
                    :dataSource="managerList"
                    bordered
                >
                    <span slot="price" slot-scope="text">
                        <span>￥{{ text }}</span>
                    </span>
                    <span slot="action" slot-scope="record">
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
            <a-tab-pane tab="酒店管理" key="2">
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
                                  size="small"
                                  @click="setHotelManager(record)"
                                  v-if="record.managerId>0" >
                            更改管理员
                        </a-button>
                        <a-button
                                type="primary"
                                size="small"
                                @click="setHotelManager(record)"
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
                            <a-button type="danger" size="small">删除酒店</a-button>
                        </a-popconfirm>
                    </span>
                </a-table>
            </a-tab-pane>
        </a-tabs>
        <AddManagerModal></AddManagerModal>
        <AddHotelModal></AddHotelModal>
        <SetHotelManagerModal></SetHotelManagerModal>
    </div>
</template>
<script>
import { mapGetters, mapMutations, mapActions } from 'vuex'
import AddManagerModal from './components/addManagerModal'
import AddHotelModal from '../hotelManager/components/addHotelModal'
import SetHotelManagerModal from './components/setHotelManagerModal'
const columns = [
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
        title: '用户id',
        dataIndex: 'id',
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



]
export default {
    name: 'manageHotel',
    data(){
        return {
            formLayout: 'horizontal',
            pagination: {},
            columns,
            columns1,
            data: [],
            form: this.$form.createForm(this, { name: 'manageUser' }),
        }
    },
    components: {
        AddManagerModal,
        AddHotelModal,
        SetHotelManagerModal,
    },
    computed: {
        ...mapGetters([
            'addManagerModalVisible',
            'addHotelModalVisible',
            'managerList',
            'adminHotelList',
            'setHotelManagerModalVisible',
            'HotelId',
        ])
    },
    mounted() {
      this.getManagerList(),
      this.getHotelList()
    },
    methods: {
        ...mapActions([
            'getManagerList',
            'getHotelList',
            'deleteUser',
            'deleteHotel',
            'addCommentTable',
        ]),
        ...mapMutations([
            'set_addHotelModalVisible',
            'set_addManagerModalVisible',
            'set_setHotelManagerModalVisible',
            'set_HotelId',
        ]),
        addHotel() {
            this.set_addHotelModalVisible(true)
        },
        addManager(){
            this.set_addManagerModalVisible(true)
        },
        doAddCommentTable(){
            this.addCommentTable()
        },
        setHotelManager(record){
            //console.log(this.HotelId)
            console.log(record.id);
            this.set_HotelId(record.id);
            console.log(this.HotelId)
            this.set_setHotelManagerModalVisible(true)
        },
        //
        DeleteUser(record){
            //console.log(record)
            this.deleteUser(record.id)
        },
        DeleteHotel(record){
            //console.log(record)
            this.deleteHotel(record.id)
        }
        //

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