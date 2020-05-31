<template>
    <a-modal
            :visible="setHotelManagerModalVisible"
            title="设置管理员"
            cancelText="取消"
            okText="确定"
            @cancel="cancel"
            @ok="handleSubmit"
    >
        <a-table
                :columns="columns"
                :dataSource="managerList"
                bordered
        >

            <span slot="action" slot-scope="record">
                        <a-popconfirm
                                title="确定选择该用户作为本酒店的管理员吗？"
                                @confirm="SetHotelManager(record)"
                                okText="确定"
                                cancelText="取消"
                        >
                            <a-button type="primany" >选择用户</a-button>
                        </a-popconfirm>
                    </span>
        </a-table>
    </a-modal>
</template>
<script>
import { mapGetters, mapMutations, mapActions } from 'vuex'

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
        title: '用户id',
        dataIndex: 'id',
    },
    {
        title: '操作',
        key: 'action',
        scopedSlots: { customRender: 'action' },
    },
];
export default {
    name:'setHotelManagerModal',

    data(){
        return {
            columns,
        }
    },
    computed: {

        ...mapGetters([
            'setHotelManagerModalVisible',
            'managerList',
            'HotelId',
        ])
    },
    mounted() {
        this.getManagerList()
    },
    methods: {
        ...mapGetters([
            'HotelId',
        ]),
        ...mapMutations([
            'setHotelManager',
            'set_setHotelManagerModalVisible',

        ]),
        ...mapActions([
            'setHotelManager',
            'getManagerList',

        ]),
        SetHotelManager(record){
            console.log(this.HotelId)
            console.log(record.id)
           this.setHotelManager(this.HotelId,record.id)

        },
        cancel() {
            this.set_setHotelManagerModalVisible(false)
        },
        handleSubmit(e) {
            e.preventDefault();

        },
    }
}

</script>