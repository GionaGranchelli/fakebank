import React, {Component} from 'react';
import 'bootstrap/dist/css/bootstrap.css';
import {Container, Row, Col, Badge, Table} from "react-bootstrap";
import {Link} from "react-router-dom";

class Home extends Component {

    render() {

        return (
            <Container fluid="md">
                <Row><Col>&nbsp;</Col></Row>
                <Row>
                    <Col>
                        <h1><Badge variant="secondary">Fake Home</Badge></h1>
                    </Col>
                </Row>
                <Row><Col>&nbsp;</Col></Row>
                <Row>
                    <Col>
                        <div className="sectionbody">
                            <div className="paragraph">
                                <h4>fakebank receives monthly deliveries of customer statement records. This information
                                    is delivered in two formats, CSV and XML. These records need to be validated.</h4>
                            </div>
                        </div>
                    </Col>
                </Row>
                <Row>
                    <Col>&nbsp;</Col>
                </Row>
                <Row>
                    <Col>
                        <div>
                            <div><h5>The format of the file is a simplified version of the MT940 format. The format is as follows:</h5></div>
                            <br/>
                            <Table>
                                <thead>
                                <tr>
                                    <th>Field</th>
                                    <th>Description</th>
                                </tr>
                                </thead>
                                <tbody>
                                <tr>
                                    <td><p>Transaction reference</p></td>
                                    <td><p>A numeric value</p></td>
                                </tr>
                                <tr>
                                    <td><p>Account number</p></td>
                                    <td><p>An IBAN</p></td>
                                </tr>
                                <tr>
                                    <td><p>Start Balance</p></td>
                                    <td><p>The starting balance in Euros</p></td>
                                </tr>
                                <tr>
                                    <td><p>Mutation</p></td>
                                    <td><p>Either an addition (+) or a deduction (-)</p></td>
                                </tr>
                                <tr>
                                    <td><p>Description</p></td>
                                    <td><p>Free text</p></td>
                                </tr>
                                <tr>
                                    <td><p>End Balance</p></td>
                                    <td><p>The end balance in Euros</p></td>
                                </tr>
                                </tbody>
                            </Table>
                        </div>
                    </Col>
                    <Col>
                        <div>
                            <div>
                                <h5>If you want to start just click here <Link to="/statement">Statement Upload</Link></h5>
                            </div>
                        </div>
                    </Col>
                </Row>
            </Container>);
    }
}

export default Home;
