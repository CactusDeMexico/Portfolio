import unittest

import aiounittest

from BookReservation import BookReservation
from config import DefaultConfig


class MyTestCase(aiounittest.AsyncTestCase):
    def setUp(self, config=DefaultConfig()):
        super().setUp()
        self.CONFIG = config
    def test_is_luis_app_id(self):
        self.assertEqual(type(self.CONFIG.LUIS_APP_ID), str)
        self.assertTrue(len(self.CONFIG.LUIS_APP_ID) == 36)

    def test_is_luis_prediction_key(self):
        self.assertEqual(type(self.CONFIG.LUIS_API_KEY), str)
        self.assertTrue(len(self.CONFIG.LUIS_API_KEY) == 32)

    def test_is_app_insights_conn_string(self):
        self.assertEqual(type(self.CONFIG.APPINSIGHTS_INSTRUMENTATION), str)
        self.assertTrue(len(self.CONFIG.APPINSIGHTS_INSTRUMENTATION) == 127)

    def test_is_bot_application_id(self):
        self.assertEqual(type(self.CONFIG.APP_ID), str)
        self.assertTrue(len(self.CONFIG.APP_ID) == 36)

    def test_is_bot_app_password_test(self):
        self.assertEqual(type(self.CONFIG.APP_PASSWORD), str)
        self.assertTrue(len(self.CONFIG.APP_PASSWORD) == 37)

class Test_Bot_Activities_Test(aiounittest.AsyncTestCase):
    def setUp(self, booking = BookReservation, config=DefaultConfig(), ):
        super().setUp()
        self.CONFIG = config
        self.booking = BookReservation()
    def test_i_check_dates_departure_already_passed(self):
        self.assertEqual(self.booking.CheckDate("05-02-2021",None), False )

    def test_i_check_dates_departure_passed_wrong_format(self):
        self.assertEqual(self.booking.CheckDate("2022-05-05",None), False )

    def test_i_check_dates_return_passed_departure_dates(self):
        self.assertEqual(self.booking.CheckDate("05-06-2021","15-06-2021"), False )
    def test_i_check_dates_return_passed_departure_dates(self):
        self.assertEqual(self.booking.CheckDate("05-06-2021","15-06-2021"), False )


if __name__ == '__main__':
    unittest.main()
