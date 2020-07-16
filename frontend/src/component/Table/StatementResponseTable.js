import React, {Component} from "react";
import {Table} from "react-bootstrap";

export default class StatementResponseTable extends Component {

    render() {
        const {response} = this.props;
        if (response.length ===  0) {
            return <h3> Load a File! </h3>;
        }
        return (
            <div>
                <h3> Error in your Records </h3><br/>
                <Table striped bordered hover>
                    <thead>
                    <tr>
                        <th>Transaction ID</th>
                        <th>Description</th>
                        <th>Failure</th>
                    </tr>
                    </thead>
                    {this.renderBody(response)}
                </Table>
            </div>
        );
    }

    renderBody(response) {
        const records = response.map(record => {
            return <tr>
                <td>{record.transactionId}</td>
                <td>{record.transactionDescription}</td>
                <td>{record.failureDescription}</td>
            </tr>
        });
        return <tbody>{records}</tbody>
    }
}

