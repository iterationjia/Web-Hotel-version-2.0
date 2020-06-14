<template>
    <a-modal
        :visible="manageRoomModalVisible"
        title="客房管理"
        cancelText="取消"
        okText="确定"
        @cancel="cancel"
        @ok="cancel"
    >
        <a-button @click="handleAddRoomType">新增客房种类</a-button>
        <a-table
                style="margin-top: 30px"
                :columns="columns"
                :dataSource="hotelDetail.rooms"
                rowKey="id"
                bordered
        >
            <span slot="price" slot-scope="text,record">
                {{text}}
                <a-popconfirm
                    placement="top"
                    ok-text="确定"
                    cancel-text="取消"
                    @confirm="handleEditPrice(record.id)"
                >
                    <span slot="title">
                        <a-input-number v-model="priceValue"></a-input-number>
                    </span>
                    <a-button shape="circle" size="small" icon="edit"></a-button>
                </a-popconfirm>
            </span>
            <span slot="total" slot-scope="text,record">
                {{text}}
                <a-popconfirm
                        placement="top"
                        ok-text="确定"
                        cancel-text="取消"
                        @confirm="handleEditTotal(record.id)"
                >
                    <span slot="title">
                        <a-input-number v-model="totalValue"></a-input-number>
                    </span>
                    <a-button shape="circle" size="small" icon="edit"></a-button>
                </a-popconfirm>
            </span>
            <span slot="curNum" slot-scope="text,record">
                {{text}}
                <a-popconfirm
                        placement="top"
                        ok-text="确定"
                        cancel-text="取消"
                        @confirm="handelEditCurNum(record.id)"
                >
                    <span slot="title">
                        <a-input-number v-model="curNumValue"></a-input-number>
                    </span>
                    <a-button shape="circle" size="small" icon="edit"></a-button>
                </a-popconfirm>
            </span>
            <span slot="action" slot-scope="record">
                <a-popconfirm
                        title="确定要删除吗？"
                    placement="top"
                    ok-text="确定"
                    cancel-text="取消"
                        @confirm="handleDel(record.id)"
                >

                    <a-button type="danger" size="small">删除</a-button>
                </a-popconfirm>
            </span>
        </a-table>
    </a-modal>
</template>
<script>
import { mapGetters, mapMutations, mapActions } from 'vuex'
const columns = [
    {
        title: "房间类型",
        dataIndex: 'roomType',
        key: 'roomType',
    },
    {
        title: "价格",
        key: 'price',
        dataIndex: 'price',
        scopedSlots: { customRender: 'price' },
    },
    {
        title: "总房间",
        dataIndex: 'total',
        key: 'total',
        scopedSlots: { customRender: 'total' },
    },
    {
        title: "现有房间",
        dataIndex: 'curNum',
        key: 'curNum',
        scopedSlots: { customRender: 'curNum' },
    },
    {
        title: "操作",
        key: 'action',
        scopedSlots: { customRender: 'action' },
    }
]
export default {
    name: 'manageRoomModal',
    data() {
        return {
            columns,
            value:0,
            priceValue:0,
            totalValue:0,
            curNumValue:0
        }
    },
    computed: {
        ...mapGetters([
            'manageRoomModalVisible',
            'hotelDetail'
        ])
    },

    mounted() {
    },
    methods: {
        ...mapMutations([
            'set_addRoomModalVisible',
            'set_manageRoomModalVisible',
        ]),
        ...mapActions([
            'getHotelDetail',
            'editRoomPrice',
            'editRoomTotal',
            'editRoomCurNum',
            'deleteRoom'
        ]),
        cancel() {
            this.set_manageRoomModalVisible(false)
        },
        handleAddRoomType(){
            this.set_manageRoomModalVisible(false)
            this.set_addRoomModalVisible(true)
        },
        handleEditPrice(roomId){
            this.editRoomPrice({
                roomId: roomId,
                val: this.priceValue
            })
        },
        handleEditTotal(roomId){
            this.editRoomTotal({
                roomId: roomId,
                val: this.priceValue
            })
        },
        handelEditCurNum(roomId){
            this.editRoomCurNum({
                roomId: roomId,
                val: this.priceValue
            })
        },
        handleDel(roomId){
            this.deleteRoom(roomId)
        }
    }
}
</script>