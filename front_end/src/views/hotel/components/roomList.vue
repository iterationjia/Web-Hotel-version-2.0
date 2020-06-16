<template>
    <div class="room-list">
        <div class="filter">

        </div>
        <div class="list">
            <a-table
                :columns="columns"
                :dataSource="rooms"
            >
                <span slot="price" slot-scope="text">
                    <span>￥{{ text }}</span>
                </span>
                <span slot="bedType" slot-scope="text, record">
                    <span>{{record.roomType=='大床房'?'一张大床':record.roomType=='双床房'?'两张单人床':'一张大床和一张单人床'}}</span>
                </span>
                <span slot="peopleNum" slot-scope="text, record">
                    <span>{{record.roomType=='大床房'?'1或2':record.roomType=='双床房'?'2':'3'}}</span>
                </span>
                <span slot="action" slot-scope="text, record">
                    <a-button type="primary" :disabled="record.curNum==0" @click="order(record)">预定</a-button>
                </span>
            </a-table>
        </div>
        <OrderModal></OrderModal>
    </div>
</template>
<script>
import { mapGetters, mapActions, mapMutations } from 'vuex'
import OrderModal from './orderModal'
const columns = [
    {  
      title: '房型',
      dataIndex: 'roomType',
      key: 'roomType',
    },
    {
      title: '床型',
      dataIndex: 'bedType',
      key: 'bedType',
      scopedSlots: { customRender: 'bedType'}
    },
    {
      title: '剩余房间',
      dataIndex: 'curNum',
      key: 'curNum'
    },
    {
      title: '建议入住人数',
      key: 'peopleNum',
      dataIndex: 'peopleNum',
      scopedSlots: { customRender: 'peopleNum'}
    },
    {
      title: '房价',
      key: 'price',
      dataIndex: 'price',
      scopedSlots: { customRender: 'price'}
    },
    {
      title: 'Action',
      key: 'action',
      scopedSlots: { customRender: 'action' },
    },
  ];
export default {
    name:'roomList',
    props: {
        rooms: {
            type: Array
        }
    },
    data() {
        return {
            columns,
        }
    },
    components: {
        OrderModal
    },
    computed: {
        ...mapGetters([
            'orderModalVisible'
        ])
    },
    monuted() {

    },
    methods: {
        ...mapMutations([
            'set_orderModalVisible',
            'set_currentOrderRoom'
        ]),
        ...mapActions([

        ]),
        order(record) {
            this.set_currentOrderRoom(record)
            this.set_orderModalVisible(true)
        }
    }
}
</script>