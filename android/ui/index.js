import React from 'react-native'
import User from './user/'
import Draft from './draft/'
import scene from './scene'

const {Component, Navigator} = React

let records = []

export default class Ui extends Component {
  render() {
    return <Navigator
      initialRoute={{stage: 'user'}}
      renderScene={this._renderScene.bind(this)}
      configureScene={() => scene}
    />
  }

  _renderScene(route, navigator) {
    switch (route.stage) {
      case 'user':
        return <User
          records={records}
          navigateToDraft={this._navigateToDraft.bind(this, navigator)}
        />
      case 'draft':
        return <Draft
          navigateToUser={this._navigateToUser.bind(this, navigator)}
          createRecord={this._createRecord.bind(this)}
        />
    }
  }

  _navigateToDraft(navigator) {
    navigator.push({stage: 'draft'})
  }

  _navigateToUser(navigator) {
    navigator.pop()
  }

  _createRecord(record) {
    records.push(record)
  }
}

