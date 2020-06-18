<template>
    <a-table
            :columns="columns"
            :dataSource="orders"
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
        <a-tag color="blue" slot="orderState" slot-scope="text">
            {{ text }}
        </a-tag>
    </a-table>
</template>

<script>
    const columns = [
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
            title: '房间数',
            dataIndex: 'roomNum',
        },
        {
            title: '房价',
            dataIndex: 'price',
            scopedSlots: { customRender: 'price' }
        },
        {
            title: '状态',
            filters: [{ text: '已预订', value: '已预订' }, { text: '已撤销', value: '已撤销' }, { text: '已入住', value: '已入住' }],
            onFilter: (value, record) => record.orderState.includes(value),
            dataIndex: 'orderState',
            scopedSlots: { customRender: 'orderState' }
        }
    ];
    export default {
        name: "hotelOrderList",
        props: {
            orders: {
                type: Array
            }
        },
        data(){
            return{
                columns
            }
        }
    }
</script>

<style scoped>

</style>