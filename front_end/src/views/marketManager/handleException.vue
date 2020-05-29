<template>
    <div class="handleException-wrapper">
        <a-tabs>
            <a-tab-pane tab="异常订单" key="1">
                <a-table
                        :columns="columns1"
                        :dataSource="managerExceptionalOrderList"
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
                        <a-divider type="vertical"></a-divider>
                        <a-button type="danger" size="small" @click="showModal">撤销异常订单</a-button>
                            <a-modal
                                    title="撤销异常订单"
                                    :visible="visible"
                                    @ok="handleOk(record.id)"
                                    @cancel="handleCancel"
                            >
                                <div class="code-box-demo">
                                    <a-icon type="smile" :style="{ fontSize: '20px'}"/>  恢复信用值比例
                                    <a-slider v-model="inputValue" :min="0.5" :max="1.0" :step="0.1" @change="onChange" @afterChange="onAfterChange" />
                                </div>
                            </a-modal>
                    </span>
                </a-table>
            </a-tab-pane>
        </a-tabs>
    </div>
</template>

<script>
import {mapActions, mapGetters, mapMutations} from "vuex";
const columns1 = [
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
    name: 'handleException',
    data(){
        return {
            columns1,
            visible: false,
            inputValue:1
        }
    },
    components: {

    },
    computed: {
        ...mapGetters([
            'managerExceptionalOrderList',
        ]),
    },
    async mounted() {
        await this.getAllOrders()
    },
    methods: {
        ...mapMutations([
            'set_orderList',
        ]),
        ...mapActions([
            'getAllOrders',
            'deleteOrder',
        ]),

        showModal() {
            this.visible = true;
        },
        handleOk(orderId) {
            //借用price存比例
            console.log('这是一条console')
            const data = {
                id: orderId,
                price: this.inputValue
            }
            this.deleteOrder(data)
        },

        handleCancel(e) {
            console.log('Clicked cancel button');
            this.visible = false;
        },
        onChange(value) {
            console.log('change: ', value);
        },
        onAfterChange(value) {
            console.log('afterChange: ', value);
        },
    }
}
</script>
<style scoped lang="less">
    .handleException-wrapper {
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
    .handleException-wrapper {
        .ant-tabs-bar {
            padding-left: 30px
        }
    }
</style>
<style scoped lang="less">
    .code-box-demo .ant-slider {
        margin-bottom: 16px;
    }
</style>