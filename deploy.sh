
#!/bin/bash

set -e

S3_BUCKET=$1
DEPLOY_BUCKET=$2
ARTIFACT=$3
ENV=$4
# upload artifact to s3
aws s3 cp dir/"${ARTIFACT}" s3://"${DEPLOY_BUCKET}"/"${ARTIFACT}"
##Create application
aws cloudformation package --template-file elasticbeanstalk_sqs_application.yml \
    --output-template-file elasticbeanstalk_sqs_application_output.yml --s3-bucket \
    "${S3_BUCKET}"
# note the || exit 0, this will not fail the build if the command fails.  I know...but unfortunately the
# resource in this template is not able to be deployed idempotently =/ (not a word)
aws cloudformation deploy --template-file elasticbeanstalk_sqs_application.yml --capabilities CAPABILITY_IAM --stack-name eb-sqs-app || exit 0
## create deployment
aws cloudformation package --template-file elasticbeanstalk_sqs.yml --output-template-file \
    elasticbeanstalk_sqs_output.yml --s3-bucket "${S3_BUCKET}"

aws cloudformation deploy --template-file elasticbeanstalk_sqs_output.yml --capabilities CAPABILITY_IAM \
    --stack-name eb-sqs --parameter-overrides VpcId=vpc-c7aa77be DeploymentBucket=elasticbeanstalk-moodle-deployables \
    DeploymentVersion="${ARTIFACT}" Environment="${ENV}"

