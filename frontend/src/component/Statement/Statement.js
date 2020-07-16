import React, {Component} from 'react';
import 'bootstrap/dist/css/bootstrap.css';
import {Badge, Col, Container, Row} from "react-bootstrap";
import {FormFile} from "react-bootstrap";
import Feedback from "react-bootstrap/Feedback";
import Button from "react-bootstrap/Button";
import {postFileToValidate} from "../../Api/api"
import Alert from "react-bootstrap/Alert";
import StatementResponseTable from "../Table/StatementResponseTable";

class Statement extends Component {
    constructor(props) {
        super(props);
        this.state = {
            file: undefined,
            response: [],
            error: {
                code: undefined,
                errorDescription: undefined
            }
        };
        this.onFormSubmit = this.onFormSubmit.bind(this);
        this.onChange = this.onChange.bind(this);
        this.fileUpload = this.fileUpload.bind(this);
        this.renderError = this.renderError.bind(this);
        this.renderForm = this.renderForm.bind(this);
    }

    onChange(e) {
        this.setState({file: e.target.files[0]})
    }

    fileUpload(file) {
        const formData = new FormData();
        formData.append('file', file);
        return postFileToValidate(formData);
    }

    onFormSubmit(e) {
        const {file} = this.state;
        e.preventDefault();
        if (file) {
            this.fileUpload(file)
                .then(response => {
                    if (response.code) {
                        this.setState({
                            error: {code: response.code, errorDescription: response.errorDescription},
                            response: []
                        });
                    } else {
                        this.setState({response: response, error: {code: undefined, errorDescription: undefined}});
                    }
                });
        } else {
            this.setState({error: {code: 123, errorDescription: "Please select a file"}, response: []});
        }

    }

    render() {
        const {response, error} = this.state;
        return (
            <Container fluid="md">
                <Row><Col>&nbsp;</Col></Row>
                <Row>
                    <Col>
                        <h1>
                            <Badge variant="secondary">Statement Uploader</Badge>
                        </h1>
                    </Col>
                </Row>
                {this.renderForm()}

                {error.code && this.renderError(error.errorDescription)}

                <StatementResponseTable response={response}/>
            </Container>

        )
    }

    renderError(errorDescription) {
        const header = [<Row><Col><h3>Failed Transaction</h3></Col></Row>];
        const subHeader = [<Row><Col>&nbsp;</Col></Row>];
        const errorContent = [<Row>
            <Col>
                <Alert variant="danger"
                       onClose={() => this.setState({error: {code: undefined, errorDescription: undefined}})}
                       dismissible>
                    <Alert.Heading>Oh snap! You got an error!</Alert.Heading>
                    <p>{errorDescription}</p>
                </Alert>
            </Col>
        </Row>];
        return header
            .concat(subHeader)
            .concat(errorContent);
    }

    renderForm() {
        const header = [<Row><Col>&nbsp;</Col></Row>];
        const form = [<Row>
            <Col>
                <div>
                    <form className="form-group" onSubmit={this.onFormSubmit}>
                        <FormFile.Label>Please select a valid XML or CSV file</FormFile.Label>
                        <input className="form-control-file"
                               id="exampleFormControlFile1"
                               type="file"
                               onChange={this.onChange}/>
                        <Feedback type="invalid">This is required</Feedback>
                        <br/>
                        <Button className="btn btn-primary" type="submit">Upload your Statement</Button>
                    </form>
                </div>
            </Col>
        </Row>];
        return header.concat(form);
    }
}

export default Statement;